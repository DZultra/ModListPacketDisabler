package net.dzultra.mixin;

import me.shedaniel.autoconfig.AutoConfig;
import net.dzultra.ModConfig;
import net.dzultra.ModListPacketDisabler;
import net.minecraft.network.protocol.common.custom.ModListPayload;
import net.minecraft.network.protocol.common.custom.PropertyMap;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(value = ModListPayload.class, priority = 2000)
public class ModListPayloadMixin {
    @Unique
    private static final ModListPayload TEST_PAYLOAD =
            new ModListPayload(Map.of(
                    Identifier.parse(ModListPacketDisabler.MOD_ID),
                    new PropertyMap(Map.of(Identifier.parse("mod-list-packet-disabler"), "1.0.0")))
            );

    @Unique
    private static final ModListPayload EMPTY_PAYLOAD = new ModListPayload(Map.of());

    @Inject(
            method = "createClient",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void forceEmptyModList(
            CallbackInfoReturnable<ModListPayload> cir
    ) {
        ModConfig modConfig = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        if (modConfig.sendEmptyModList) {
            if (modConfig.enableModLogs) ModListPacketDisabler.LOGGER.info("[ModListPacketDisabler] Sending Empty Mod List Payload");
            cir.setReturnValue(EMPTY_PAYLOAD);
        }
    }

}
