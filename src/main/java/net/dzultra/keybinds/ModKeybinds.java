package net.dzultra.keybinds;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;

public class ModKeybinds {
    public static KeyMapping openConfigKeybind;
    private static final KeyMapping.Category ModListPacketDisablerCategory = new KeyMapping.Category(Identifier.parse("mod-list-packet-disabler"));


    public static void register() {
        openConfigKeybind = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.mod-list-packet-disabler.open_settings",
                InputConstants.Type.KEYBOARD,
                InputConstants.KEY_O,
                ModListPacketDisablerCategory
        ));
    }
}
