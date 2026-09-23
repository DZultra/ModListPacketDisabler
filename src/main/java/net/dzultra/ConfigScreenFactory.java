package net.dzultra;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ConfigScreenFactory {
    public static Screen create(Screen parent) {
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.literal("Mod List Packet Disabler Settings"))
                .setSavingRunnable(() ->
                        AutoConfig.getConfigHolder(ModConfig.class).save()
                );

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        // ─────────────── General ───────────────-
        var general = builder.getOrCreateCategory(Component.literal("General"));

        general.addEntry(entryBuilder
                .startBooleanToggle(Component.literal("Send Mod List Packet"), config.sendModList)
                .setSaveConsumer(value -> config.sendModList = value)
                .build());

        general.addEntry(entryBuilder
                .startBooleanToggle(Component.literal("Send an empty Mod List Packet"), config.sendEmptyModList)
                .setSaveConsumer(value -> config.sendEmptyModList = value)
                .build());

        return builder.build();
    }
}
