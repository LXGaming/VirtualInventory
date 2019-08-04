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

package io.github.lxgaming.virtualinventory.plugin.mixin.core.network;

import io.github.lxgaming.virtualinventory.plugin.manager.PacketManager;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(targets = "net/minecraft/network/PacketThreadUtil$1")
public abstract class PacketThreadUtilMixin {
    
    /**
     * Intercept packets before Sponge's {@link org.spongepowered.common.event.tracking.PhaseTracker PhaseTracker}.
     *
     * @see <a href="https://github.com/SpongePowered/SpongeCommon/blob/stable-7/src/main/java/org/spongepowered/common/mixin/core/util/PacketThreadUtil$1Mixin.java">PacketThreadUtil$1Mixin</a>
     */
    @Inject(method = "run()V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/network/Packet;processPacket(Lnet/minecraft/network/INetHandler;)V"
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    private void onProcessPacket(CallbackInfo callbackInfo, Packet packetIn, INetHandler processor) {
        if (PacketManager.processPacket(processor, packetIn)) {
            callbackInfo.cancel();
        }
    }
}