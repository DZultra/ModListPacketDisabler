package net.dzultra.config;

import dev.isxander.yacl3.api.ListOption;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.StringControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.dzultra.ModListPacketDisabler;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModConfig {
    public static final ConfigClassHandler<ModConfig> HANDLER = ConfigClassHandler.createBuilder(ModConfig.class)
            .id(ModListPacketDisabler.id("config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve(ModListPacketDisabler.MOD_ID + ".json5"))
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry
    public boolean sendModList = true;

    @SerialEntry
    public boolean sendEmptyModList = true;

    @SerialEntry
    public boolean sendCustomModList = false;

    @SerialEntry
    public boolean enableModLogs = false;

    @SerialEntry
    public List<String> modList = List.of("some_mod:1.0.0", "another_mod:1.2.3");

    public static ModConfig get() {
        return HANDLER.instance();
    }

    public static Option<Boolean> createSendModListOption() {
        return createBooleanOption(
                "Send Mod List",
                "When disabled, the mod list will not be sent to the server. Servers can detect this and may flag you for it. It is suggested to just send an empty mod list instead of disabling this option.",
                HANDLER.defaults().sendModList,
                () -> get().sendModList,
                newVal -> get().sendModList = newVal
        );
    }

    public static Option<Boolean> createSendEmptyModListOption() {
        return createBooleanOption(
                "Send Empty Mod List",
                "When enabled, an empty mod list will be sent to the server. This overrides the 'Send Custom Mod List' option. This option is only used when 'Send Mod List' is enabled.",
                HANDLER.defaults().sendEmptyModList,
                () -> get().sendEmptyModList,
                newVal -> get().sendEmptyModList = newVal
        );
    }

    public static Option<Boolean> createSendCustomModListOption() {
        return createBooleanOption(
                "Send Custom Mod List",
                "When enabled, the custom mod list defined in the 'Custom Mod List' section will be sent to the server. This option is only used when 'Send Mod List' is enabled. This option is overridden by the 'Send Empty Mod List' option.",
                HANDLER.defaults().sendCustomModList,
                () -> get().sendCustomModList,
                newVal -> get().sendCustomModList = newVal
        );
    }

    public static Option<Boolean> createEnableModLogsOption() {
        return createBooleanOption(
                "Send Mod Logs",
                "When enabled, debugging logs concerning this mod will be present in the client log. This is a debugging option.",
                HANDLER.defaults().enableModLogs,
                () -> get().enableModLogs,
                newVal -> get().enableModLogs = newVal
        );
    }

    public static ListOption<String> createModListOption() {
        return ListOption.<String>createBuilder()
                .name(Component.literal("Custom Mod List"))
                .description(OptionDescription.of(Component.literal("This option allows you to customize the mod list. Always use the format <name>:<version> for each entry. This option is only used when 'Send Mod List' and 'Send Custom Mod List' is enabled.")))
                .binding(HANDLER.defaults().modList, () -> get().modList, newVal -> get().modList = newVal)
                .controller(StringControllerBuilder::create)
                .initial("")
                .maximumNumberOfEntries(8192)
                .minimumNumberOfEntries(1)
                .build();
    }

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
