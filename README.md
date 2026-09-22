# Mod List Packet Disabler

A lightweight Minecraft Fabric Client Mod that lets you toggle whether the `minecraft:mod_list` packet is sent to servers.

As of **Minecraft 26.4 Snapshot 1**, the client sends a new `minecraft:mod_list` custom packet payload when joining a server.
Full changelog: https://www.minecraft.net/en-us/article/minecraft-26-4-snapshot-1

## Disclaimer

This Mod uses the newest Fabric API for 26.4-snaptshot-1 as well as Cloth Config & Mod Menu versions which are not intended for this version of Minecraft.
Stuff can break, things might crash, any issues you encounter are not my responsibility. Use at your own risk.
Any issue reports or pull request are welcome and will be considered, but I cannot guarantee that I will be able to fix or implement them.
I will try to keep this mod updated, but I cannot guarantee that it will always be compatible with the latest versions of Minecraft or Fabric API.

## What this mod does

By default, the Mod List Payload is empty. However, other Mods may add their own data to this packet, which can be used by servers to detect what mods you have installed.
As some players don't want specific mods who choose to add this functionality to be broadcasted to every server they connect to, this mod allows you to disable the sending of the `minecraft:mod_list` packet entirely.

## Configuration

The toggle can be changed via Cloth Config (accessible through Mod Menu)

Set `sendModList` to `false` to stop the packet from being sent, or `true` to re-enable it. (false by default)

## Compatibility

| Minecraft Version | Supported                 |
|-------------------|---------------------------|
| 26.4 Snapshot 1+  | ✅                         |
| Earlier versions  | ❌ (packet does not exist) |

## Dependencies

| Cloth Config | Mod Menu         |
|--------------|------------------|
| \>=26.3.158  | \>=21.0.0-beta.1 |


## Why would I want to disable this?

- Privacy 
- Avoiding automated mod detection on servers
- General preference to minimize what your client reports to servers
