package net.dzultra.config;

import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import net.minecraft.network.chat.Component;

public class ModOptionGroups {
    private static final OptionGroup mainOptionsGroup = OptionGroup.createBuilder()
            .name(Component.literal("Main Options"))
            .description(OptionDescription.of(Component.literal("These are the main options for the mod.")))
            .option(ModConfig.sendMostListOption)
            .option(ModConfig.sendEmptyModListOption)
            .build();

    private static final OptionGroup debugOptionsGroup = OptionGroup.createBuilder()
            .name(Component.literal("Debug Options"))
            .description(OptionDescription.of(Component.literal("These are the debug options for the mod.")))
            .option(ModConfig.enableModLogsOption)
            .build();

    public static OptionGroup getMainOptionsGroup() {
        return mainOptionsGroup;
    }

    public static OptionGroup getDebugOptionsGroup() {
        return debugOptionsGroup;
    }
}
