package gg.iyk.dungeon.api;

import java.util.UUID;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/** Fired after a grant is saved. */
public final class RuneShardsGrantedEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final UUID player;
    private final int amount;
    private final int balanceAfter;
    private final String source;
    private final String reason;
    private final String caller;

    public RuneShardsGrantedEvent(UUID player, int amount, int balanceAfter, String source, String reason,
                                  String caller) {
        this.player = player;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.source = source;
        this.reason = reason;
        this.caller = caller;
    }

    public UUID player() { return player; }
    public int amount() { return amount; }
    public int balanceAfter() { return balanceAfter; }
    public String source() { return source; }
    public String reason() { return reason; }
    /** Plugin name; {@code dungeon} for {@code /da rune-shards give}. */
    public String caller() { return caller; }
    @Override public HandlerList getHandlers() { return HANDLERS; }
    public static HandlerList getHandlerList() { return HANDLERS; }
}
