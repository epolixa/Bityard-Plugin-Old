package com.epolixa.bityard;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Bityard extends JavaPlugin
{
    private ProtocolManager protocolManager;
    //private static AFK afk;

    // Startup
    @Override
    public void onEnable()
    {
        sendLog("Enabling...");

        // Add packet events
        protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Server.CHAT) {
            @Override
            public void onPacketSending(PacketEvent e) {
                if (e.getPacketType() == PacketType.Play.Server.CHAT)
                {
                    try
                    {
                        PacketContainer packet = e.getPacket();
                        WrappedChatComponent chat = packet.getChatComponents().read(0);
                        String msg = chat.getJson();
                        if (msg.contains("{\"color\":\"blue\",\"text\":\"δ\"}")) // is message from discordmc, prefix from config
                        {
                            String nick = msg.substring(msg.indexOf("\\u003c") + 6, msg.indexOf("\\u003e"));

                            for (String playerName : getServer().getScoreboardManager().getMainScoreboard().getEntries())
                            {
                                if (nick.equals(playerName)) // found a player name! attempt to apply scoreboard color
                                {
                                    nick = "\"},{\"color\":\"" + getServer().getScoreboardManager().getMainScoreboard().getEntryTeam(playerName).getName() + "\",\"text\":\"" + nick + "\"},{\"color\":\"white\",\"text\":\"";
                                    msg = msg.substring(0, msg.indexOf("\\u003c") + 6) + nick + msg.substring(msg.indexOf("\\u003e"), msg.length());
                                }
                            }
                            chat.setJson(msg);
                            packet.getChatComponents().write(0, chat);
                        }
                    }
                    catch (Exception ex) {sendLog("Error while handling chat: " + ex.toString());}
                }
            }
        });

        // Register orphan event listeners
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new ChatListener(this), this); // chat colors

        // Start child classes
        sendLog("Starting child classes");
        //afk = new AFK(this); afk.start(); // AFK

        sendLog("Enabled");
    }

    // Shutdown
    @Override
    public void onDisable()
    {
        sendLog("Disabling...");

        // Stop child classes
        sendLog("Stopping child classes");
        //afk.stop(); // AFK

        sendLog("Disabled");
    }

    public void sendLog(String msg) {getLogger().info("[Bityard] " + msg);}
}
