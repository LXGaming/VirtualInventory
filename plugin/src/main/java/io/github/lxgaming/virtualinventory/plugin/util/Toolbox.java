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
import io.github.lxgaming.virtualinventory.api.inventory.ButtonTypes;
import io.github.lxgaming.virtualinventory.api.inventory.ClickType;
import io.github.lxgaming.virtualinventory.api.inventory.ClickTypes;
import org.spongepowered.api.Sponge;

public class Toolbox {
    
    public static ButtonType getButtonType(int button, ClickType clickType) {
        if (clickType == ClickTypes.SWAP) {
            if (button == 0) {
                return ButtonTypes.KEY_ONE;
            } else if (button == 1) {
                return ButtonTypes.KEY_TWO;
            } else if (button == 2) {
                return ButtonTypes.KEY_THREE;
            } else if (button == 3) {
                return ButtonTypes.KEY_FOUR;
            } else if (button == 4) {
                return ButtonTypes.KEY_FIVE;
            } else if (button == 5) {
                return ButtonTypes.KEY_SIX;
            } else if (button == 6) {
                return ButtonTypes.KEY_SEVEN;
            } else if (button == 7) {
                return ButtonTypes.KEY_EIGHT;
            } else if (button == 8) {
                return ButtonTypes.KEY_NINE;
            }
        } else {
            if (button == 0) {
                return ButtonTypes.MOUSE_PRIMARY;
            } else if (button == 1) {
                return ButtonTypes.MOUSE_SECONDARY;
            }
        }
        
        throw new UnsupportedOperationException();
    }
    
    public static ClickType getClickType(net.minecraft.inventory.ClickType clickType) {
        return Sponge.getRegistry().getType(ClickType.class, clickType.name()).orElseThrow(UnsupportedOperationException::new);
    }
}