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

package io.github.lxgaming.virtualinventory.plugin.util;

import io.github.lxgaming.virtualinventory.api.inventory.ButtonType;
import io.github.lxgaming.virtualinventory.api.inventory.ClickTypes;
import io.github.lxgaming.virtualinventory.api.inventory.container.VirtualContainer;
import io.github.lxgaming.virtualinventory.api.inventory.slot.EmptySlot;
import io.github.lxgaming.virtualinventory.api.inventory.slot.PlayerSlot;
import io.github.lxgaming.virtualinventory.api.inventory.slot.VirtualSlot;
import io.github.lxgaming.virtualinventory.plugin.manager.PacketManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.common.entity.EntityUtil;
import org.spongepowered.common.item.inventory.util.ItemStackUtil;

import javax.annotation.Nullable;
import java.util.List;

public final class ProxyContainer extends Container {
    
    private final VirtualContainer virtualContainer;
    
    public ProxyContainer(VirtualContainer virtualContainer) {
        this.virtualContainer = virtualContainer;
    }
    
    @Override
    protected final Slot addSlotToContainer(Slot slot) {
        throw new UnsupportedOperationException("Not supported");
    }
    
    @Override
    public final void addListener(IContainerListener listener) {
        throw new UnsupportedOperationException("Not supported");
    }
    
    @Override
    public final NonNullList<ItemStack> getInventory() {
        return NonNullList.withSize(0, ItemStack.EMPTY);
    }
    
    @Override
    public final void removeListener(IContainerListener listener) {
        throw new UnsupportedOperationException("Not supported");
    }
    
    @Override
    public final void detectAndSendChanges() {
        // Called by Entity:onUpdate
        // throw new UnsupportedOperationException("Not supported");
    }
    
    @Override
    public final boolean enchantItem(EntityPlayer entityPlayer, int id) {
        return false;
    }
    
    @Nullable
    @Override
    public final Slot getSlotFromInventory(IInventory inventory, int index) {
        throw new UnsupportedOperationException("Not supported");
    }
    
    @Override
    public final Slot getSlot(int slotId) {
        throw new UnsupportedOperationException("Not supported");
    }
    
    @Override
    public final ItemStack transferStackInSlot(EntityPlayer entityPlayer, int index) {
        return ItemStack.EMPTY;
    }
    
    @Override
    public final ItemStack slotClick(int index, int button, ClickType nativeClickType, EntityPlayer entityPlayer) {
        Player player = EntityUtil.toPlayer(entityPlayer);
        io.github.lxgaming.virtualinventory.api.inventory.ClickType clickType = Toolbox.getClickType(nativeClickType);
        ButtonType buttonType = Toolbox.getButtonType(button, clickType);
        VirtualSlot slot = getSlot(index, entityPlayer);
        
        if (slot.getItemStack().isEmpty()) {
            slot.actionPerformed(buttonType, clickType, player);
            return ItemStack.EMPTY;
        }
        
        if (clickType == ClickTypes.CLONE && entityPlayer.capabilities.isCreativeMode && entityPlayer.inventory.getItemStack().isEmpty()) {
            PacketManager.sendPacket(entityPlayer, new SPacketSetSlot(-1, -1, ItemStack.EMPTY));
            slot.actionPerformed(buttonType, clickType, player);
            return ItemStack.EMPTY;
        }
        
        if (clickType == ClickTypes.PICKUP) {
            PacketManager.sendPacket(entityPlayer, new SPacketSetSlot(this.windowId, index, ItemStackUtil.toNative(slot.getItemStack())));
            PacketManager.sendPacket(entityPlayer, new SPacketSetSlot(-1, -1, ItemStack.EMPTY));
            slot.actionPerformed(buttonType, clickType, player);
            return ItemStackUtil.toNative(slot.getItemStack());
        }
        
        if (clickType == ClickTypes.PICKUP_ALL) {
            slot.actionPerformed(buttonType, clickType, player);
            return ItemStack.EMPTY;
        }
        
        if (clickType == ClickTypes.QUICK_CRAFT) {
            slot.actionPerformed(buttonType, clickType, player);
            return ItemStack.EMPTY;
        }
        
        if (clickType == ClickTypes.QUICK_MOVE) {
            if (entityPlayer.openContainer.windowId == this.windowId) {
                PacketManager.sendContents(entityPlayer, this);
            }
            
            slot.actionPerformed(buttonType, clickType, player);
            return ItemStack.EMPTY;
        }
        
        if (clickType == ClickTypes.SWAP && button >= 0 && button < 9) {
            ItemStack playerItemStack = entityPlayer.inventory.getStackInSlot(button);
            if (slot.getItemStack().isEmpty() && playerItemStack.isEmpty()) {
                return ItemStack.EMPTY;
            }
            
            int playerSlotIndex = entityPlayer.inventory.mainInventory.size() + button;
            PacketManager.sendPacket(entityPlayer, new SPacketSetSlot(this.windowId, index, ItemStackUtil.toNative(slot.getItemStack())));
            PacketManager.sendPacket(entityPlayer, new SPacketSetSlot(0, playerSlotIndex, playerItemStack));
            slot.actionPerformed(buttonType, clickType, player);
            return ItemStack.EMPTY;
        }
        
        if (clickType == ClickTypes.THROW) {
            PacketManager.sendPacket(entityPlayer, new SPacketSetSlot(this.windowId, index, ItemStackUtil.toNative(slot.getItemStack())));
            slot.actionPerformed(buttonType, clickType, player);
            return ItemStack.EMPTY;
        }
        
        return ItemStack.EMPTY;
    }
    
    @Override
    public final boolean canMergeSlot(ItemStack itemStack, Slot slot) {
        return false;
    }
    
    @Override
    public final void onContainerClosed(EntityPlayer entityPlayer) {
        InventoryPlayer inventoryPlayer = entityPlayer.inventory;
        if (!inventoryPlayer.getItemStack().isEmpty()) {
            entityPlayer.dropItem(inventoryPlayer.getItemStack(), false);
            inventoryPlayer.setItemStack(ItemStack.EMPTY);
        }
    }
    
    @Override
    protected final void clearContainer(EntityPlayer entityPlayer, World world, IInventory inventory) {
        throw new UnsupportedOperationException("Not supported");
    }
    
    @Override
    public final void onCraftMatrixChanged(IInventory inventory) {
        throw new UnsupportedOperationException("Not supported");
    }
    
    @Override
    public final void putStackInSlot(int index, ItemStack itemStack) {
        throw new UnsupportedOperationException("Not supported");
    }
    
    @Override
    public final void setAll(List<ItemStack> itemStacks) {
        throw new UnsupportedOperationException("Not supported");
    }
    
    @Override
    public final void updateProgressBar(int id, int data) {
        throw new UnsupportedOperationException("Not supported");
    }
    
    @Override
    public final short getNextTransactionID(InventoryPlayer inventoryPlayer) {
        throw new UnsupportedOperationException("Not supported");
    }
    
    @Override
    public final boolean getCanCraft(EntityPlayer entityPlayer) {
        throw new UnsupportedOperationException("Not supported");
    }
    
    @Override
    public final void setCanCraft(EntityPlayer entityPlayer, boolean canCraft) {
        throw new UnsupportedOperationException("Not supported");
    }
    
    @Override
    protected final boolean mergeItemStack(ItemStack itemStack, int startIndex, int endIndex, boolean reverseDirection) {
        return false;
    }
    
    @Override
    protected final void resetDrag() {
        throw new UnsupportedOperationException("Not supported");
    }
    
    @Override
    public final boolean canDragIntoSlot(Slot slot) {
        return false;
    }
    
    @Override
    protected final void slotChangedCraftingGrid(World world, EntityPlayer entityPlayer, InventoryCrafting inventoryCrafting, InventoryCraftResult inventoryCraftResult) {
        throw new UnsupportedOperationException("Not supported");
    }
    
    @Override
    public final boolean canInteractWith(EntityPlayer player) {
        return true;
    }
    
    private VirtualSlot getSlot(int index, EntityPlayer entityPlayer) {
        if (index < 0 || index > (getVirtualContainer().getSize() + entityPlayer.inventory.mainInventory.size())) {
            return EmptySlot.EMPTY;
        }
        
        if (index < getVirtualContainer().getSize()) {
            return getVirtualContainer().getSlot(index);
        }
        
        return new PlayerSlot(ItemStackUtil.fromNative(entityPlayer.inventory.mainInventory.get(index - getVirtualContainer().getSize())));
    }
    
    public final VirtualContainer getVirtualContainer() {
        return virtualContainer;
    }
}