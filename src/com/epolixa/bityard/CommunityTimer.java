package com.epolixa.bityard;

import org.bukkit.entity.Player;

import java.util.UUID;

public class CommunityTimer implements Runnable
{
    private final Community community;
    private final Bityard bityard;
    private final AFK afk;

    private final int range = 64;

    public CommunityTimer(Community community, AFK afk)
    {
        this.community = community;
        this.bityard = community.getPlugin();
        this.afk = afk;
    }

    public void run()
    {
        if (bityard.getServer().getOnlinePlayers().size() > 1)
        {
            for (Player player : bityard.getServer().getOnlinePlayers())
            {
                UUID uuid = player.getUniqueId();
                int group = 0;

                for (Player other : bityard.getServer().getOnlinePlayers())
                {
                    if (other.getLocation().distance(player.getLocation()) <= range)
                    {
                        group++;
                    }
                }

                if (group > 0 && !afk.getAFKstatus(uuid))
                {
                    int groupedTime = community.getGroupedTime(uuid) + 1;
                    community.setAloneTime(uuid, 0);
                    if (groupedTime >= 60) // a minute of grouped time
                    {
                        community.addCommunityScore(player, group);
                        community.setGroupedTime(uuid, 0);
                    }
                    else
                    {
                        community.setGroupedTime(uuid, groupedTime);
                    }
                }
                else
                {
                    int aloneTime = community.getAloneTime(uuid) + 1;
                    community.setGroupedTime(uuid, 0);
                    if (aloneTime >= 60)
                    {
                        community.addCommunityScore(player, bityard.getServer().getOnlinePlayers().size() - 1);
                        community.setAloneTime(uuid, 0);
                    }
                    else
                    {
                        community.setAloneTime(uuid, aloneTime);
                    }
                }
            }
        }
    }
}
