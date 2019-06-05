/*
 * Copyright 2019 Alex Thomson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.lxgaming.virtualinventory.plugin.manager;

import com.google.common.base.Preconditions;
import io.github.lxgaming.virtualinventory.api.inventory.slot.VirtualSlot;
import io.github.lxgaming.virtualinventory.plugin.VirtualInventoryPlugin;
import io.github.lxgaming.virtualinventory.plugin.util.ProxyContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.server.SPacketConfirmTransaction;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.network.play.server.SPacketWindowItems;
import net.minecraft.util.NonNullList;
import org.spongepowered.common.SpongeImpl;
import org.spongepowered.common.SpongeImplHooks;
import org.spongepowered.common.item.inventory.util.ItemStackUtil;
import org.spongepowered.common.util.ThreadUtil;

public final class PacketManager {
    
    public static boolean processPacket(INetHandler netHandler, Packet packet) {
        Preconditions.checkState(SpongeImplHooks.isMainThread(),
                "PacketManager called from off main thread (current='%s', expected='%s')!",
                ThreadUtil.getDescription(Thread.currentThread()),
                ThreadUtil.getDescription(SpongeImpl.getServer().getServerThread())
        );
        
        if (netHandler instanceof NetHandlerPlayServer) {
            EntityPlayerMP player = ((NetHandlerPlayServer) netHandler).player;
            if (packet instanceof CPacketClickWindow) {
                return processClickWindow(player, (CPacketClickWindow) packet);
            }
        }
        
        return false;
    }
    
    public static void sendPacket(EntityPlayer player, Packet packet) {
        if (player instanceof EntityPlayerMP) {
            ((EntityPlayerMP) player).connection.sendPacket(packet);
            return;
        }
        
        VirtualInventoryPlugin.getInstance().getLogger().warn("Cannot send packet to {}", player.getName());
    }
    
    public static void sendContents(EntityPlayer player, ProxyContainer proxyContainer) {
        VirtualInventoryPlugin.getInstance().getLogger().info("sendContents");
        NonNullList<ItemStack> items = NonNullList.create();
        
        proxyContainer.getVirtualContainer().getSlots().stream()
                .map(VirtualSlot::getItemStack)
                .map(ItemStackUtil::toNative)
                .forEach(items::add);
        
        items.addAll(player.inventory.mainInventory);
        
        sendPacket(player, new SPacketWindowItems(proxyContainer.windowId, items));
        sendPacket(player, new SPacketSetSlot(-1, -1, ItemStack.EMPTY));
    }
    
    private static boolean processClickWindow(EntityPlayerMP player, CPacketClickWindow packet) {
        if (player.openContainer instanceof ProxyContainer && player.openContainer.windowId == packet.getWindowId()) {
            player.markPlayerActive();
            /*
            if (player.isSpectator()) {
                player.closeContainer();
                return true;
            }
            */
            
            ItemStack itemStack = player.openContainer.slotClick(packet.getSlotId(), packet.getUsedButton(), packet.getClickType(), player);
            if (!ItemStack.areItemStacksEqual(packet.getClickedItem(), itemStack)) {
                player.sendAllContents(player.openContainer, player.openContainer.getInventory());
                return true;
            }
            
            player.connection.sendPacket(new SPacketConfirmTransaction(packet.getWindowId(), packet.getActionNumber(), true));
            player.isChangingQuantityOnly = true;
            // player.openContainer.detectAndSendChanges();
            player.updateHeldItem();
            player.isChangingQuantityOnly = false;
            return true;
        }
        
        return false;
    }
}