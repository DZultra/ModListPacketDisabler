package net.dzultra;

import net.dzultra.config.ModConfig;
import net.dzultra.keybinds.KeybindHandler;
import net.dzultra.keybinds.ModKeybinds;
import net.fabricmc.api.ClientModInitializer;

public class ModListPacketDisablerClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModConfig.HANDLER.load();
        ModKeybinds.register();
        KeybindHandler.register();
    }
}
