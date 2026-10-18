package net.dzultra.config;

import dev.isxander.yacl3.api.ConfigCategory;
import net.minecraft.network.chat.Component;

public class ModConfigCategories {
    private static final ConfigCategory booleanCategory = ConfigCategory.createBuilder()
            .name(Component.literal("Boolean Options"))
            .tooltip(Component.literal("These options are boolean values that can be toggled on or off."))
            .group(ModOptionGroups.getMainOptionsGroup())
            .group(ModOptionGroups.getDebugOptionsGroup())
            .build();

    private static final ConfigCategory customContentCategory = ConfigCategory.createBuilder()
            .name(Component.literal("Custom Content"))
            .tooltip(Component.literal("These options allow you to customize the content of the mod list."))
            .option(ModConfig.modListOption)
            .build();

    public static ConfigCategory getBooleanCategory() {
        return booleanCategory;
    }

    public static ConfigCategory getCustomContentCategory() {
        return customContentCategory;
    }
}
