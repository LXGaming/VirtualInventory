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

package io.github.lxgaming.virtualinventory.plugin;

import com.google.inject.Inject;
import io.github.lxgaming.virtualinventory.api.VirtualInventory;
import io.github.lxgaming.virtualinventory.api.inventory.ButtonType;
import io.github.lxgaming.virtualinventory.api.inventory.ClickType;
import io.github.lxgaming.virtualinventory.api.inventory.container.VirtualContainer;
import io.github.lxgaming.virtualinventory.api.util.Reference;
import io.github.lxgaming.virtualinventory.plugin.manager.PacketManager;
import io.github.lxgaming.virtualinventory.plugin.registry.ButtonTypeRegistryModule;
import io.github.lxgaming.virtualinventory.plugin.registry.ClickTypeRegistryModule;
import io.github.lxgaming.virtualinventory.plugin.util.ProxyContainer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketOpenWindow;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameConstructionEvent;
import org.spongepowered.api.event.game.state.GameLoadCompleteEvent;
import org.spongepowered.api.event.game.state.GameStoppedEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.common.entity.EntityUtil;
import org.spongepowered.common.text.SpongeTexts;

@Plugin(
        id = Reference.ID,
        name = Reference.NAME,
        version = Reference.VERSION,
        description = Reference.DESCRIPTION,
        authors = {Reference.AUTHORS},
        url = Reference.WEBSITE
)
public class VirtualInventoryPlugin extends VirtualInventory {
    
    @Inject
    private PluginContainer pluginContainer;
    
    @Inject
    private Logger logger;
    
    @Listener
    public void onConstruction(GameConstructionEvent event) {
        Sponge.getRegistry().registerModule(ButtonType.class, new ButtonTypeRegistryModule());
        Sponge.getRegistry().registerModule(ClickType.class, new ClickTypeRegistryModule());
    }
    
    @Listener
    public void onLoadComplete(GameLoadCompleteEvent event) {
        getLogger().info("{} v{} has loaded", Reference.NAME, Reference.VERSION);
    }
    
    @Listener
    public void onStopped(GameStoppedEvent event) {
        getLogger().info("{} v{} has stopped", Reference.NAME, Reference.VERSION);
    }
    
    @Override
    public void openContainer(Player player, VirtualContainer virtualContainer) {
        EntityPlayerMP entityPlayer = EntityUtil.toNative(player);
        ProxyContainer proxyContainer = new ProxyContainer(virtualContainer);
        if (entityPlayer.openContainer != entityPlayer.inventoryContainer) {
            entityPlayer.closeContainer();
        }
        
        entityPlayer.getNextWindowId();
        entityPlayer.connection.sendPacket(new SPacketOpenWindow(
                entityPlayer.currentWindowId,
                virtualContainer.getId(),
                SpongeTexts.toComponent(virtualContainer.getTitle()),
                virtualContainer.getSize()
        ));
        
        entityPlayer.openContainer = proxyContainer;
        entityPlayer.openContainer.windowId = entityPlayer.currentWindowId;
        PacketManager.sendContents(entityPlayer, proxyContainer);
    }
    
    public static VirtualInventoryPlugin getInstance() {
        return (VirtualInventoryPlugin) VirtualInventory.getInstance();
    }
    
    public PluginContainer getPluginContainer() {
        return pluginContainer;
    }
    
    public Logger getLogger() {
        return logger;
    }
}