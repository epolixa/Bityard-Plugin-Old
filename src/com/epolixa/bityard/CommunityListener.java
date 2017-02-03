package com.epolixa.bityard;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class CommunityListener implements Listener
{
    private final Community community;
    private final Bityard bityard;

    public CommunityListener(Community community)
    {
        this.community = community;
        this.bityard = community.getPlugin();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        community.setAloneTime(event.getPlayer().getUniqueId(), 0);
        community.setGroupedTime(event.getPlayer().getUniqueId(), 0);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        community.removeAloneTime(event.getPlayer().getUniqueId());
        community.removeGroupedTime(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerKicked(PlayerKickEvent event)
    {
        community.removeAloneTime(event.getPlayer().getUniqueId());
        community.removeGroupedTime(event.getPlayer().getUniqueId());
    }

}
