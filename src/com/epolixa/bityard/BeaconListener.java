package com.epolixa.bityard;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class BeaconListener implements Listener
{

    private final Bityard bityard;

    public BeaconListener(Bityard bityard)
    {
        this.bityard = bityard;
    }

    @EventHandler
    public void onBeaconApplyEffect()
    {

    }



}
