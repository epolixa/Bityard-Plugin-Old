package com.epolixa.bityard;

import org.bukkit.entity.Player;

import java.util.UUID;

public class CommunityTimer implements Runnable
{
    private final Community community;
    private final Bityard bityard;
    private final AFK afk;

    private final int range = 64;
    private final int delay = 60;
    private final int maxAloneMinutes = 60;

    public CommunityTimer(Community community, AFK afk)
    {
        this.community = community;
        this.bityard = community.getPlugin();
        this.afk = afk;
    }

    public void run()
    {
        try
        {
            if (bityard.getServer().getOnlinePlayers().size() > 1)
            {
                for (Player player : bityard.getServer().getOnlinePlayers())
                {
                    UUID uuid = player.getUniqueId();
                    int group = 0;

                    for (Player other : bityard.getServer().getOnlinePlayers()) {
                        if (other.getUniqueId() != uuid && other.getWorld() == player.getWorld() && !afk.getAFKstatus(other.getUniqueId()))
                        {
                            if (other.getLocation().distance(player.getLocation()) <= range) {
                                group++;
                            }
                        }
                    }

                    if (group > 0 && !afk.getAFKstatus(uuid))
                    {
                        int groupedTime = community.getGroupedTime(uuid) + 1;
                        community.setGroupedTime(uuid, groupedTime);
                        community.setAloneTime(uuid, 0);
                        if (groupedTime > 60 && groupedTime % delay == 0) // a minute of grouped time
                        {
                            community.addCommunityScore(player, group);
                        }
                    }
                    else
                    {
                        int aloneTime = community.getAloneTime(uuid) + 1;
                        community.setAloneTime(uuid, aloneTime);
                        community.setGroupedTime(uuid, 0);
                        if (aloneTime > 60 && aloneTime % delay == 0)
                        {
                            int numPlayers = bityard.getServer().getOnlinePlayers().size() - 1; // -1 to not count self
                            int aloneMinutes = aloneTime / delay;

                            if (aloneMinutes >= maxAloneMinutes) // one hour
                            {
                                community.addCommunityScore(player, -numPlayers);
                            }
                            else
                            {
                                community.addCommunityScore(player, -((int)Math.ceil((aloneMinutes * numPlayers) / maxAloneMinutes)));
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            community.sendLog(e.toString());
        }

    }
}
