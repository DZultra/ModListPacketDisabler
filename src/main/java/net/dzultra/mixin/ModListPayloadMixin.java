package net.dzultra.mixin;

import net.dzultra.ModListPacketDisabler;
import net.dzultra.config.ModConfig;
import net.dzultra.config.ModEntry;
import net.minecraft.network.protocol.common.custom.ModListPayload;
import net.minecraft.network.protocol.common.custom.PropertyMap;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.LinkedHashMap;

@Mixin(value = ModListPayload.class, priority = 2000)
public class ModListPayloadMixin {
    // Intentionally unused: kept as a fixed payload for manual testing
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
        if (ModConfig.get().sendEmptyModList) {
            if (ModConfig.get().enableModLogs) ModListPacketDisabler.LOGGER.info("[ModListPacketDisabler] Sending Empty Mod List Payload");
            cir.setReturnValue(EMPTY_PAYLOAD);
        } else if (ModConfig.get().sendCustomModList) {
            if (ModConfig.get().enableModLogs) ModListPacketDisabler.LOGGER.info("[ModListPacketDisabler] Sending Custom Mod List Payload");
            Map<Identifier, PropertyMap> entries = new LinkedHashMap<>();
            for (ModEntry mod : ModConfig.get().customModList) {
                Identifier id = Identifier.tryParse(mod.id());
                if (mod.id().isEmpty() || mod.version().isEmpty() || id == null) {
                    if (ModConfig.get().enableModLogs) ModListPacketDisabler.LOGGER.warn("[ModListPacketDisabler] Skipping invalid Custom Mod List entry '{}'", mod);
                    continue;
                }
                if (entries.containsKey(id)) {
                    if (ModConfig.get().enableModLogs) ModListPacketDisabler.LOGGER.warn("[ModListPacketDisabler] Skipping duplicate Custom Mod List entry '{}'", mod);
                    continue;
                }
                entries.put(id, new PropertyMap(Map.of(
                        Identifier.parse("version"), // Property Name
                        mod.version() // Property Value
                )));
            }
            cir.setReturnValue(new ModListPayload(entries));
        }
    }
}
