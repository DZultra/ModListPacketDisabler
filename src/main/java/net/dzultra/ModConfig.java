package net.dzultra;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import java.util.List;

@Config(name = ModListPacketDisabler.MOD_ID)
public class ModConfig implements ConfigData {
    public Boolean sendModList = true;
    public Boolean sendEmptyModList = true;
    public Boolean enableModLogs = true;

    public List<String> customStrings = List.of("");
}
