package net.dzultra.config;

import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import net.minecraft.network.chat.Component;

public class ModOptionGroups {
    public static OptionGroup getMainOptionsGroup() {
        return OptionGroup.createBuilder()
                .name(Component.literal("Main Options"))
                .description(OptionDescription.of(Component.literal("These are the main options for the mod.")))
                .option(ModConfig.createSendModListOption())
                .option(ModConfig.createSendEmptyModListOption())
                .option(ModConfig.createSendCustomModListOption())
                .build();
    }

    public static OptionGroup getDebugOptionsGroup() {
        return OptionGroup.createBuilder()
                .name(Component.literal("Debug Options"))
                .description(OptionDescription.of(Component.literal("These are the debug options for the mod.")))
                .option(ModConfig.createEnableModLogsOption())
                .build();
    }
}
