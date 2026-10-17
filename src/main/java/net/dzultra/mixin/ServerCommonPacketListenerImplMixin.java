package net.dzultra.mixin;

import net.dzultra.config.ModConfig;
import net.dzultra.ModListPacketDisabler;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.ModListPayload;
import net.minecraft.server.network.ServerCommonPacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerCommonPacketListenerImpl.class)
public class ServerCommonPacketListenerImplMixin {
    @Inject(
            method = "handleCustomPayload",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/server/network/ClientDebugInfo;appendModInfo(Ljava/util/Map;)V"
            )
    )
    private void inspectModList(
            ServerboundCustomPayloadPacket packet,
            CallbackInfo ci
    ) {
        if (packet.payload() instanceof ModListPayload payload) {
            ModListPacketDisabler.LOGGER.info(
                    "[ModListPacketDisabler] ModListPayload entries: {}",
                    payload.entries()
            );
        }
    }
}
