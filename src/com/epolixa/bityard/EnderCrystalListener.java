package com.epolixa.bityard;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.List;
import java.util.Random;

public class EnderCrystalListener implements Listener
{
    private final Bityard bityard;

    public EnderCrystalListener(Bityard bityard)
    {
        this.bityard = bityard;
    }

    @EventHandler
    public void onMobSpawn(EntitySpawnEvent event)
    {
        try
        {
            Entity entity = event.getEntity();
            if
            (
                (
                    entity.getType() == EntityType.ZOMBIE ||
                    entity.getType() == EntityType.HUSK ||
                    entity.getType() == EntityType.SKELETON ||
                    entity.getType() == EntityType.WITHER_SKELETON ||
                    entity.getType() == EntityType.STRAY ||
                    entity.getType() == EntityType.CREEPER ||
                    entity.getType() == EntityType.SPIDER ||
                    entity.getType() == EntityType.CAVE_SPIDER ||
                    entity.getType() == EntityType.SLIME ||
                    entity.getType() == EntityType.ENDERMAN ||
                    entity.getType() == EntityType.WITCH ||
                    entity.getType() == EntityType.BLAZE ||
                    entity.getType() == EntityType.GHAST ||
                    entity.getType() == EntityType.MAGMA_CUBE ||
                    entity.getType() == EntityType.ZOMBIE_VILLAGER ||
                    entity.getType() == EntityType.PIG_ZOMBIE ||
                    entity.getType() == EntityType.VEX
                )
            )
            {
                List<Entity> nearbyEntities = entity.getNearbyEntities(64, 64, 64);
                for (Entity e : nearbyEntities)
                {
                    if (e.getType() == EntityType.ENDER_CRYSTAL)
                    {
                        Random random = new Random();
                        entity.getWorld().spawnParticle(Particle.DRAGON_BREATH, entity.getLocation().add(0, 0.4, 0), 8, 0.4, 1, 0.4, 0.01);
                        e.getWorld().spawnParticle(Particle.DRAGON_BREATH, e.getLocation().add(0, 0.4, 0), 8, 0.4, 1, 0.4, 0.01);
                        entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_EVOCATION_ILLAGER_PREPARE_SUMMON, SoundCategory.AMBIENT, 0.4f, random.nextFloat() + 1);
                        e.getWorld().playSound(e.getLocation(), Sound.ENTITY_EVOCATION_ILLAGER_PREPARE_SUMMON, SoundCategory.AMBIENT, 0.4f, random.nextFloat() + 1);
                        event.setCancelled(true);
                    }
                }
            }

        }
        catch (Exception e)
        {
            bityard.sendLog(e.toString());
        }
    }
}
