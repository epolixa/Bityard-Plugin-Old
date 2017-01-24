package com.epolixa.bityard;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Bityard extends JavaPlugin
{
    private static AFK afk;

    // Startup
    @Override
    public void onEnable()
    {
        getLogger().info("Enabled");

        // Register orphan event listeners
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new ChatListener(this), this); // chat colors

        // Start child classes
        getLogger().info("Starting child classes");
        afk = new AFK(this); afk.start(); // AFK
    }

    // Shutdown
    @Override
    public void onDisable()
    {
        // Stop child classes
        getLogger().info("Stopping child classes");
        afk.stop(); // AFK

        getLogger().info("Disabled");
    }

    public void colorNameByTeam(Player player)
    {
        if (player.getScoreboard().getEntryTeam(player.getName()) != null)
        {
            ChatColor c;
            switch (player.getScoreboard().getEntryTeam(player.getName()).getName()) {
                case "black":
                    c = ChatColor.BLACK;
                    break;
                case "dark_blue":
                    c = ChatColor.DARK_BLUE;
                    break;
                case "dark_green":
                    c = ChatColor.DARK_GREEN;
                    break;
                case "dark_aqua":
                    c = ChatColor.DARK_AQUA;
                    break;
                case "dark_red":
                    c = ChatColor.DARK_RED;
                    break;
                case "dark_purple":
                    c = ChatColor.DARK_PURPLE;
                    break;
                case "gold":
                    c = ChatColor.GOLD;
                    break;
                case "gray":
                    c = ChatColor.GRAY;
                    break;
                case "dark_gray":
                    c = ChatColor.DARK_GRAY;
                    break;
                case "blue":
                    c = ChatColor.BLUE;
                    break;
                case "green":
                    c = ChatColor.GREEN;
                    break;
                case "aqua":
                    c = ChatColor.AQUA;
                    break;
                case "red":
                    c = ChatColor.RED;
                    break;
                case "light_purple":
                    c = ChatColor.LIGHT_PURPLE;
                    break;
                case "yellow":
                    c = ChatColor.YELLOW;
                    break;
                case "white":
                    c = ChatColor.WHITE;
                    break;
                default:
                    c = ChatColor.WHITE;
            }
            player.setDisplayName(c + ChatColor.stripColor(player.getDisplayName()) + ChatColor.WHITE);
        }
    }
}
