package com.epolixa.bityard;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scoreboard.Team;

public class ChatListener implements Listener
{
    private final Bityard bityard;

    public ChatListener(Bityard bityard)
    {
        this.bityard = bityard;
    }

    // fix chat name colors
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e)
    {
        bityard.colorNameByTeam(e.getPlayer());
    }


}
