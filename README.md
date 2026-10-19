# Mod List Packet Disabler

A lightweight Minecraft Fabric Client Mod that lets you toggle whether the newer `minecraft:mod_list` packet is sent to servers and allows you to modify its contents.

As of **Minecraft 26.4 Snapshot 1**, the client sends a new `minecraft:mod_list` custom packet payload when joining a server which modders can use to allow for server-side mod detection of their mod.
Full changelog: https://www.minecraft.net/en-us/article/minecraft-26-4-snapshot-1

## Disclaimer

This Mod uses a newer Fabric API version for 26.4-snaptshot-3.
Stuff can break, things might crash, any issues you encounter are not my responsibility. Use at your own risk.
Any issue reports or pull request are welcome and will be considered, but I cannot guarantee that I will be able to fix or implement them.
I will try to keep this mod updated, but I cannot guarantee that it will always be compatible with the latest versions of Minecraft or Fabric API.

## What this mod does

By default, the Mod List Payload is empty. However, other Mods may add their own data to this packet, which can be used by servers to detect what mods you have installed.
As some players don't want specific mods who choose to add this functionality to be broadcasted to every server they connect to, this mod allows you to either:
* choose to force the packet to be sent with an empty payload (even if other mods add data to it),
* disable the sending of the `minecraft:mod_list` packet entirely,
* send a custom payload with configurable mod data,
* or leave the default behavior (send the packet with whatever data is added by other mods).

## Why would I want to disable this?

- Privacy
- Avoiding automated mod detection on servers
- General preference to minimize what your client reports to servers

## Configuration

The related settings can be changed via YACL (accessible through Mod Menu and by pressing the Open Settings keybind) or at `minecraft/config/mod-list-packet-disabler.json5`

* Set `sendModList` to `false` to stop the packet from being sent entirely, or `true` to re-enable it. (true by default)
* Set `sendEmptyModList` to `true` to force an empty packet being sent. (true by default, only affects the packet if `sendModList` is set to `true`, else there is no packet to send; overrides `sendCustomModList` Option)
* Set `sendCustomModList` to `true` to send a packet with custom configurable data. (false by default, only affects the packet if `sendModList` is set to `true`, else there is no packet to send; overridden by `sendEmptyModList` Option)
* Set `enableModLogs` to `true` to enable the mod logs that are printed to the console when the packet is sent or suppressed. (false by default)
* Use the Custom Mod List (`customModList` in the config file) to configure the custom data that is sent in the packet if the custom one is configured to be sent. Each entry has an `id` (for example `some_mod` or `namespace:some_mod`) and a `version`, and both can be edited in their own field in the config screen. Entries with an empty or invalid ID, an empty version or a duplicate ID are skipped.

The default settings for `sendModList` and `sendEmptyModList` are recommended for most users, because this prevents servers from detecting:
* what mods you have installed,
* whether you have disabled the sending of the packet entirely (with this mod).

## Compatibility

| Minecraft Version | Supported                 |
|-------------------|---------------------------|
| 26.4 Snapshot 3+  | ✅                         |
| 26.4 Snapshot 1+  | ✅ (Deprecated)            |
| Earlier versions  | ❌ (packet does not exist) |

## Dependencies

| YetAnotherConfigLib  | Mod Menu (Optional) |
|----------------------|---------------------|
| \>=3.9.9+26.4-fabric | \>=22.0.0-alpha.2   |

(Versions before 1.2.0 use Cloth Config API instead of YACL. Prior versions are neither supported nor recommended.)