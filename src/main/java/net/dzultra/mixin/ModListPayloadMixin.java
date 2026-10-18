package net.dzultra.mixin;

import net.dzultra.ModListPacketDisabler;
import net.dzultra.config.ModConfig;
import net.minecraft.network.protocol.common.custom.ModListPayload;
import net.minecraft.network.protocol.common.custom.PropertyMap;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.stream.Collectors;

@Mixin(value = ModListPayload.class, priority = 2000)
public class ModListPayloadMixin {
    @Unique
    private static final ModListPayload TEST_PAYLOAD =
            new ModListPayload(Map.of(
                    Identifier.parse(ModListPacketDisabler.MOD_ID), // Mod Name
                    new PropertyMap(Map.of(
                                Identifier.parse("version"), // Property Name
                                "1.2.0" // Property Value
                    ))
            ));

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
        if (ModConfig.sendEmptyModList) {
            if (ModConfig.enableModLogs) ModListPacketDisabler.LOGGER.info("[ModListPacketDisabler] Sending Empty Mod List Payload");
            cir.setReturnValue(EMPTY_PAYLOAD);
        } else if (ModConfig.sendCustomModList) {
            if (ModConfig.enableModLogs) ModListPacketDisabler.LOGGER.info("[ModListPacketDisabler] Sending Custom Mod List Payload");
            cir.setReturnValue(new ModListPayload(ModConfig.modList.stream().collect(
                    Collectors.toMap(
                            mod -> Identifier.parse(mod.split(":")[0]),
                            mod -> new PropertyMap(Map.of(
                                    Identifier.parse("version"),
                                    mod.split(":")[1]
                            ))
                    )
            )));
        }
    }
}
