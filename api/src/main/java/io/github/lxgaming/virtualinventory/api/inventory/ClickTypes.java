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

package io.github.lxgaming.virtualinventory.api.inventory;

import io.github.lxgaming.virtualinventory.api.util.Reference;

public final class ClickTypes {
    
    public static final ClickType CLONE = new ClickType(String.format("%s:%s", Reference.ID, "clone"), "Clone");
    
    public static final ClickType PICKUP = new ClickType(String.format("%s:%s", Reference.ID, "pickup"), "Pickup");
    
    public static final ClickType PICKUP_ALL = new ClickType(String.format("%s:%s", Reference.ID, "pickup_all"), "Pickup All");
    
    public static final ClickType QUICK_CRAFT = new ClickType(String.format("%s:%s", Reference.ID, "quick_craft"), "Quick Craft");
    
    public static final ClickType QUICK_MOVE = new ClickType(String.format("%s:%s", Reference.ID, "quick_move"), "Quick Move");
    
    public static final ClickType SWAP = new ClickType(String.format("%s:%s", Reference.ID, "swap"), "Swap");
    
    public static final ClickType THROW = new ClickType(String.format("%s:%s", Reference.ID, "throw"), "Throw");
    
    private ClickTypes() {
        throw new AssertionError("You should not be attempting to instantiate this class.");
    }
}