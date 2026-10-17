package net.dzultra.config;

import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class ModConfig {
    public static Boolean sendModList = true;
    public static Boolean sendEmptyModList = true;
    public static Boolean enableModLogs = false;

    public static Option<Boolean> sendMostListOption = createBooleanOption(
            "Send Mod List",
            "When disabled, the mod list will not be sent to the server. Servers can detect this and may flag you for it. It is suggested to just send an empty mod list instead of disabling this option.",
            true,
            () -> sendModList,
            newVal -> sendModList = newVal
    );

    public static Option<Boolean> sendEmptyModListOption = createBooleanOption(
            "Send Empty Mod List",
            "When enabled, an empty mod list will be sent to the server.",
            true,
            () -> sendEmptyModList,
            newVal -> sendEmptyModList = newVal
    );

    public static Option<Boolean> enableModLogsOption = createBooleanOption(
            "Send Mod Logs",
            "When enabled, debugging logs concerning this mod will be present in the client log. This is a debugging option.",
            false,
            () -> enableModLogs,
            newVal -> enableModLogs = newVal
    );


    private static Option<Boolean> createBooleanOption(
            String name,
            String description,
            Boolean defaultValue,
            java.util.function.Supplier<Boolean> getter,
            Consumer<Boolean> setter
    ) {
        return Option.<Boolean>createBuilder()
                .name(Component.literal(name))
                .description(OptionDescription.of(Component.literal(description)))
                .binding(defaultValue, getter, setter)
                .controller(ModConfig::createBooleanController)
                .build();
    }

    private static BooleanControllerBuilder createBooleanController(Option<Boolean> option) {
        return BooleanControllerBuilder.create(option)
                .formatValue(val -> val ? Component.literal("Enabled") : Component.literal("Disabled"))
                .coloured(true);
    }
}
