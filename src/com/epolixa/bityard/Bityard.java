package com.epolixa.bityard;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Bityard extends JavaPlugin
{
    private ProtocolManager protocolManager;
    private static AFK afk;
    private static Community community;

    // Startup
    @Override
    public void onEnable()
    {
        sendLog("Enabling...");

        // Add packet events
        protocolManager = ProtocolLibrary.getProtocolManager();
        addPacketListeners(protocolManager);

        // Register orphan event listeners
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new ChatListener(this), this); // chat colors
        pluginManager.registerEvents(new BeaconListener(this), this); // beacon titles
        pluginManager.registerEvents(new RandomTeleportListener(this), this); // random teleport
        pluginManager.registerEvents(new MOTDListener(this), this); // change motd from town hole

        // Start child classes
        sendLog("Starting child classes");
        afk = new AFK(this); afk.start(); // AFK
        community = new Community(this, afk); community.start(); // community score

        sendLog("Enabled");
    }

    // Shutdown
    @Override
    public void onDisable()
    {
        sendLog("Disabling...");

        // Stop child classes
        sendLog("Stopping child classes");
        afk.stop(); // AFK
        community.stop();

        sendLog("Disabled");
    }

    public void sendLog(String msg) {getLogger().info("[Bityard] " + msg);}

    public void addPacketListeners(ProtocolManager protocolManager)
    {
        // intercept discordmc messages and color names
        protocolManager.addPacketListener(new PacketAdapter(this, ListenerPriority.NORMAL, PacketType.Play.Server.CHAT) {
            @Override
            public void onPacketSending(PacketEvent event) {
                if (event.getPacketType() == PacketType.Play.Server.CHAT)
                {
                    try
                    {
                        PacketContainer packet = event.getPacket();
                        WrappedChatComponent chat = packet.getChatComponents().read(0);
                        String msg = chat.getJson();
                        if (msg.contains("{\"color\":\"blue\",\"text\":\"δ\"}")) // is message from discordmc, prefix from config
                        {
                            String nick = msg.substring(msg.indexOf("\\u003c") + 6, msg.indexOf("\\u003e"));

                            for (String playerName : getServer().getScoreboardManager().getMainScoreboard().getEntries())
                            {
                                if (playerName.contains(nick) || nick.contains(playerName)) // found a player name match! attempt to apply scoreboard color
                                {
                                    nick = "\"},{\"color\":\"" + getServer().getScoreboardManager().getMainScoreboard().getEntryTeam(playerName).getName() + "\",\"text\":\"" + playerName + "\"},{\"color\":\"white\",\"text\":\"";
                                    msg = msg.substring(0, msg.indexOf("\\u003c") + 6) + nick + msg.substring(msg.indexOf("\\u003e"), msg.length());
                                    break;
                                }
                            }
                            chat.setJson(msg);
                            packet.getChatComponents().write(0, chat);
                        }
                    }
                    catch (Exception e) {sendLog("Error while intercepting discordmc message: " + e.toString());}
                }
            }
        });
    }
}
