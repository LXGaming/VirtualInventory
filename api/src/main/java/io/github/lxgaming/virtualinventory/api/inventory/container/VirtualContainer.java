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
import io.github.lxgaming.virtualinventory.api.inventory.slot.EmptySlot;
import io.github.lxgaming.virtualinventory.api.inventory.slot.VirtualSlot;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.api.text.Text;

import java.util.Arrays;
import java.util.List;

public abstract class VirtualContainer {
    
    private final String id;
    private final int size;
    private final Text title;
    private final List<VirtualSlot> slots;
    
    protected VirtualContainer(String id, int size, Text title) {
        Preconditions.checkArgument(StringUtils.isNotBlank(id), "Id cannot be blank");
        Preconditions.checkArgument(size >= 0, "Size cannot be negative");
        this.id = id;
        this.size = size;
        this.title = title;
        
        VirtualSlot[] slots = new VirtualSlot[size];
        Arrays.fill(slots, EmptySlot.EMPTY);
        this.slots = Arrays.asList(slots);
    }
    
    public final VirtualSlot getSlot(int index) {
        return getSlots().get(index);
    }
    
    public final void setSlot(int index, VirtualSlot slot) {
        getSlots().set(index, slot);
    }
    
    public final String getId() {
        return id;
    }
    
    public final int getSize() {
        return size;
    }
    
    public final Text getTitle() {
        return title;
    }
    
    public final List<VirtualSlot> getSlots() {
        return slots;
    }
}