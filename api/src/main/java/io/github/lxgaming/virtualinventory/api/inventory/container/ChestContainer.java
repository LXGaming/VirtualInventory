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

package io.github.lxgaming.virtualinventory.api.inventory.container;

import com.google.common.base.Preconditions;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.translation.FixedTranslation;

public final class ChestContainer extends VirtualContainer {
    
    public ChestContainer(int size) {
        this(size, Text.of(new FixedTranslation("container.chest")));
    }
    
    public ChestContainer(int size, Text title) {
        super("minecraft:chest", size, title);
        Preconditions.checkArgument(size > 0 && size % 9 == 0 && size / 9 <= 6, "Invalid container size");
    }
}