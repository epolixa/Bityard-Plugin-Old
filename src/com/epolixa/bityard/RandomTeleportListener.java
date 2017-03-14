package com.epolixa.bityard;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.concurrent.ThreadLocalRandom;

public class RandomTeleportListener implements Listener
{
    private final Bityard bityard;

    public RandomTeleportListener(Bityard bityard)
    {
        this.bityard = bityard;
    }

    @EventHandler
    public void onEatChorusFruitAtSpawn(PlayerItemConsumeEvent event)
    {
        if (event.getItem().getType() == Material.CHORUS_FRUIT)
        {
            Player player = event.getPlayer();
            Location spawnLocation = new Location(player.getWorld(), 472, 65, 725);
            if (player.getLocation().distance(spawnLocation) < 2)
            {
                double minRange = 1000;
                double maxRange = 100000;
                Location targetLocation = new Location(
                        player.getWorld(),
                        ThreadLocalRandom.current().nextDouble(minRange, maxRange),
                        256,
                        ThreadLocalRandom.current().nextDouble(minRange, maxRange)
                );

                while (targetLocation.getBlock().getType() == Material.AIR)
                {
                    targetLocation.add(0, -1, 0);
                }

                player.getWorld().spawnParticle(Particle.DRAGON_BREATH, player.getLocation(), 16, 0, 1, 0, 0.1);
                player.getWorld().playSound(spawnLocation, Sound.ENTITY_ENDERMEN_TELEPORT, SoundCategory.AMBIENT, 1, 1);

                player.teleport(targetLocation);

                player.getWorld().spawnParticle(Particle.DRAGON_BREATH, player.getLocation(), 16, 0, 1, 0, 0.1);
            }
        }
    }
}
