package gg.iyk.dungeon.api;

import java.util.Map;
import java.util.UUID;
import org.bukkit.plugin.Plugin;

/**
 * Grants Rune Shards. Load from Bukkit's ServicesManager; call on the main thread.
 * Every grant is journalled and counted by caller and source.
 */
public interface RuneShardGrants {

    /** Raised only when a method or record changes in a way that can break callers. */
    int API_VERSION = 1;

    record Pathway(String caller, String source) { }

    record Totals(long shards, long grants) { }

    /** The API version this server implements. Compare with {@link #API_VERSION} at startup. */
    int apiVersion();

    /**
     * @param caller     your plugin; must be the plugin making the call
     * @param amount     1 or more
     * @param source     stable pathway id: {@code [a-z0-9][a-z0-9_.:-]{0,47}}, e.g. {@code halloween-hunt}
     * @param reason     this grant's reason, 3 to 200 characters on one line
     * @param tellPlayer send an online player Dungeon's chat line and sound
     * @return shards added; 0 only when the balance could not be saved
     * @throws IllegalArgumentException on any invalid argument
     * @throws IllegalStateException off the main thread
     */
    int grant(Plugin caller, UUID player, int amount, String source, String reason, boolean tellPlayer);

    /** All-time totals. */
    Map<Pathway, Totals> totals();
}
