package net.dzultra.keybinds;

import net.dzultra.ConfigScreenFactory;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class KeybindHandler {
    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (ModKeybinds.openConfigKeybind.consumeClick()) {
                if (client.player != null) {
                    Gui gui = Minecraft.getInstance().gui;
                    gui.setScreen(ConfigScreenFactory.create(gui.screen()));
                }
            }
        });
    }
}
