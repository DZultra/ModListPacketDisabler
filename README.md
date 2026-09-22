# Mod List Packet Disabler

A lightweight Minecraft (Java Edition) client mod that lets you toggle whether the `minecraft:mod_list` packet is sent to servers.

## Background

As of **Minecraft 26.4 Snapshot 1**, the client sends a new `minecraft:mod_list` custom packet payload when joining a server.

Full changelog: https://www.minecraft.net/en-us/article/minecraft-26-4-snapshot-1

## What this mod does

Some players don't want their mod list (or specific mods) to be broadcasted to every server they connect to.
This mod adds a toggle that determines whether the `minecraft:mod_list` packet is sent.

## Configuration

The toggle can be changed via:

- **In-game options screen:** *(if you have a mod menu / config screen integration, describe it here — e.g. Mod Menu button, keybind, etc.)*
- **Config file:** `config/mod_list_packet_toggle.json`

```json
{
  "sendModListPacket": true
}
```

Set `sendModList` to `false` to stop the packet from being sent, or `true` to re-enable it. (false by default)

## Compatibility

| Minecraft Version | Supported |
|---|---|
| 26.4 Snapshot 1+ | ✅ |
| Earlier versions | ❌ (packet does not exist) |

## Why would I want to disable this?

Some reasons players choose to turn this off:

- Privacy 
- Avoiding automated mod detection on servers
- General preference to minimize what your client reports to servers
