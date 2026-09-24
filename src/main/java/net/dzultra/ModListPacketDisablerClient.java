package net.dzultra;

import net.dzultra.keybinds.KeybindHandler;
import net.dzultra.keybinds.ModKeybinds;
import net.fabricmc.api.ClientModInitializer;

public class ModListPacketDisablerClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModKeybinds.register();
        KeybindHandler.register();
    }
}
