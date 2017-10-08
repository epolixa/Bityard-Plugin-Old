package com.epolixa.bityard;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class Community
{
    private final Bityard bityard;
    private final AFK afk;

    private static ScheduledExecutorService executor;
    private static HashMap<UUID, AtomicInteger> aloneTimeMap;
    private static HashMap<UUID, AtomicInteger> groupedTimeMap;

    public Community(Bityard bityard, AFK afk)
    {
        this.bityard = bityard;
        this.afk = afk;
    }


    public void start()
    {
		/* Register event listeners */
        bityard.getServer().getPluginManager().registerEvents(new CommunityListener(this), bityard);

		/* Initialize executor */
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(new CommunityTimer(this, afk), 0, 1, TimeUnit.SECONDS);

		/* Initialize HashMaps */
        aloneTimeMap = new HashMap<UUID, AtomicInteger>();
        for (Player p : bityard.getServer().getOnlinePlayers())
            setAloneTime(p.getUniqueId(), 0);
        groupedTimeMap = new HashMap<UUID, AtomicInteger>();
        for (Player p : bityard.getServer().getOnlinePlayers())
            setGroupedTime(p.getUniqueId(), 0);

        sendLog("Enabled");
    }


    public void stop()
    {
        executor.shutdown();

        sendLog("Disabled");
    }

    public void sendLog(String msg) {bityard.log("[Community] " + msg);}

    public Bityard getPlugin() {return bityard;}

    public int getAloneTime(UUID uuid) {return aloneTimeMap.get(uuid).get();}
    public void setAloneTime(UUID uuid, int seconds) {aloneTimeMap.put(uuid, new AtomicInteger(seconds));}
    public void removeAloneTime(UUID uuid) {aloneTimeMap.remove(uuid);}

    public int getGroupedTime(UUID uuid) {return groupedTimeMap.get(uuid).get();}
    public void setGroupedTime(UUID uuid, int seconds) {groupedTimeMap.put(uuid, new AtomicInteger(seconds));}
    public void removeGroupedTime(UUID uuid) {groupedTimeMap.remove(uuid);}


    public void addCommunityScore(Player player, int amount)
    {
        Score score = player.getScoreboard().getObjective("community").getScore(player.getName());
        int newScore = score.getScore() + amount;
        newScore = Math.max(Math.min(newScore, 1000), 0);
        score.setScore(newScore);
    }
}
