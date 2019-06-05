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

public final class ButtonTypes {
    
    public static final ButtonType MOUSE_PRIMARY = new ButtonType(String.format("%s:%s", Reference.ID, "mouse_primary"), "Primary");
    
    public static final ButtonType MOUSE_SECONDARY = new ButtonType(String.format("%s:%s", Reference.ID, "mouse_secondary"), "Secondary");
    
    public static final ButtonType KEY_ONE = new ButtonType(String.format("%s:%s", Reference.ID, "key_one"), "One");
    
    public static final ButtonType KEY_TWO = new ButtonType(String.format("%s:%s", Reference.ID, "key_two"), "Two");
    
    public static final ButtonType KEY_THREE = new ButtonType(String.format("%s:%s", Reference.ID, "key_three"), "Three");
    
    public static final ButtonType KEY_FOUR = new ButtonType(String.format("%s:%s", Reference.ID, "key_four"), "Four");
    
    public static final ButtonType KEY_FIVE = new ButtonType(String.format("%s:%s", Reference.ID, "key_five"), "Five");
    
    public static final ButtonType KEY_SIX = new ButtonType(String.format("%s:%s", Reference.ID, "key_six"), "Six");
    
    public static final ButtonType KEY_SEVEN = new ButtonType(String.format("%s:%s", Reference.ID, "key_seven"), "Seven");
    
    public static final ButtonType KEY_EIGHT = new ButtonType(String.format("%s:%s", Reference.ID, "key_eight"), "Eight");
    
    public static final ButtonType KEY_NINE = new ButtonType(String.format("%s:%s", Reference.ID, "key_nine"), "Nine");
    
    private ButtonTypes() {
        throw new AssertionError("You should not be attempting to instantiate this class.");
    }
}