package com.epolixa.bityard;

import com.sun.xml.internal.ws.api.Cancelable;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;

public class BeaconApplyEffectEvent extends BlockEvent implements Cancellable
{
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private final Entity entity;

    public BeaconApplyEffectEvent(Block block, Entity entity) {
        super(block);
        this.entity = entity;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}


