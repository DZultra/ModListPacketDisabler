package net.dzultra.mixin;

import me.shedaniel.autoconfig.AutoConfig;
import net.dzultra.ModConfig;
import net.dzultra.ModListPacketDisabler;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.ModListPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientHandshakePacketListenerImpl.class)
public abstract class ClientHandshakePacketListenerImplMixin {

    @Redirect(
            method = "handleLoginFinished",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/Connection;send(Lnet/minecraft/network/protocol/Packet;)V"
            )
    )
    private void modListPacketToggle$maybeSuppressModList(Connection connection, Packet<?> packet) {
        if (!AutoConfig.getConfigHolder(ModConfig.class).getConfig().sendModList
                && packet instanceof ServerboundCustomPayloadPacket(
                net.minecraft.network.protocol.common.custom.CustomPacketPayload payload
        )
                && payload instanceof ModListPayload) {
            ModListPacketDisabler.LOGGER.info("[ModListPacketDisabler] Suppressed ModListPayload");
            return;
        }
        ModListPacketDisabler.LOGGER.info("[ModListPacketDisabler] Sending {}", packet.getClass().getSimpleName());
        connection.send(packet);
    }
}
