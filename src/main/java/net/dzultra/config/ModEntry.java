package net.dzultra.config;

import org.jspecify.annotations.NonNull;

public record ModEntry(String id, String version) {
    public ModEntry {
        // Gson leaves fields that are missing from the config file as null
        if (id == null) id = "";
        if (version == null) version = "";
    }

    public ModEntry withId(String id) {
        return new ModEntry(id, version);
    }

    public ModEntry withVersion(String version) {
        return new ModEntry(id, version);
    }

    @Override
    public @NonNull String toString() {
        return id + ":" + version;
    }
}
