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

package io.github.lxgaming.virtualinventory.plugin.registry;

import io.github.lxgaming.virtualinventory.api.inventory.ClickType;
import io.github.lxgaming.virtualinventory.api.inventory.ClickTypes;
import io.github.lxgaming.virtualinventory.api.util.Reference;
import org.spongepowered.api.registry.AlternateCatalogRegistryModule;
import org.spongepowered.api.registry.RegistrationPhase;
import org.spongepowered.api.registry.util.DelayedRegistration;
import org.spongepowered.common.registry.type.AbstractPrefixAlternateCatalogTypeRegistryModule;

public final class ClickTypeRegistryModule extends AbstractPrefixAlternateCatalogTypeRegistryModule<ClickType> implements AlternateCatalogRegistryModule<ClickType> {
    
    public ClickTypeRegistryModule() {
        super(Reference.ID);
    }
    
    @DelayedRegistration(value = RegistrationPhase.PRE_INIT)
    @Override
    public void registerDefaults() {
        register(ClickTypes.CLONE);
        register(ClickTypes.PICKUP);
        register(ClickTypes.PICKUP_ALL);
        register(ClickTypes.QUICK_CRAFT);
        register(ClickTypes.QUICK_MOVE);
        register(ClickTypes.SWAP);
        register(ClickTypes.THROW);
    }
}