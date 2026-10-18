package net.dzultra.config;

import dev.isxander.yacl3.api.ListOption;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModConfig {
    public static Boolean sendModList = true;
    public static Option<Boolean> sendMostListOption = createBooleanOption(
            "Send Mod List",
            "When disabled, the mod list will not be sent to the server. Servers can detect this and may flag you for it. It is suggested to just send an empty mod list instead of disabling this option.",
            true,
            () -> sendModList,
            newVal -> sendModList = newVal
    );

    public static Boolean sendEmptyModList = true;
    public static Option<Boolean> sendEmptyModListOption = createBooleanOption(
            "Send Empty Mod List",
            "When enabled, an empty mod list will be sent to the server.",
            true,
            () -> sendEmptyModList,
            newVal -> sendEmptyModList = newVal
    );

    public static Boolean sendCustomModList = false;
    public static Option<Boolean> sendCustomModListOption = createBooleanOption(
            "Send Custom Mod List",
            "When enabled, the custom mod list defined in the 'Custom Mod List' section will be sent to the server. This option is only used when 'Send Mod List' is enabled.",
            false,
            () -> sendCustomModList,
            newVal -> sendCustomModList = newVal
    );

    public static Boolean enableModLogs = false;
    public static Option<Boolean> enableModLogsOption = createBooleanOption(
            "Send Mod Logs",
            "When enabled, debugging logs concerning this mod will be present in the client log. This is a debugging option.",
            false,
            () -> enableModLogs,
            newVal -> enableModLogs = newVal
    );

    public static List<String> modList = List.of("some_mod:1.0.0", "another_mod:1.2.3");
    public static ListOption<String> modListOption = ListOption.<String>createBuilder()
            .name(Component.literal("Custom Mod List"))
            .description(OptionDescription.of(Component.literal("This option allows you to customize the mod list. Always use the format <name>:<version> for each entry. This option is only used when 'Send Mod List' and 'Send Custom Mod List' is enabled.")))
            .binding(modList, () -> modList, newVal -> modList = newVal)
            .controller(StringControllerBuilder::create)
            .initial("")
            .minimumNumberOfEntries(8192)
            .build();

    private static Option<Boolean> createBooleanOption(
            String name,
            String description,
            Boolean defaultValue,
            Supplier<Boolean> getter,
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
