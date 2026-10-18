package net.dzultra.config;

import dev.isxander.yacl3.api.YetAnotherConfigLib;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ConfigScreenFactory {
    public static Screen create(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
                .title(Component.literal("Mod Config"))
                .category(ModConfigCategories.getBooleanCategory())
                .category(ModConfigCategories.getCustomContentCategory())
                .save(ModConfig.HANDLER::save)
                .build().generateScreen(parent);
    }
}
