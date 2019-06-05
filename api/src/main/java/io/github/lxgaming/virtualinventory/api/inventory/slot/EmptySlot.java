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

package io.github.lxgaming.virtualinventory.api.inventory.slot;

import io.github.lxgaming.virtualinventory.api.inventory.ButtonType;
import io.github.lxgaming.virtualinventory.api.inventory.ClickType;
import org.spongepowered.api.entity.living.player.Player;

public final class EmptySlot extends VirtualSlot {
    
    public static final EmptySlot EMPTY = new EmptySlot();
    
    private EmptySlot() {
    }
    
    @Override
    public final void actionPerformed(ButtonType buttonType, ClickType clickType, Player player) {
    }
}