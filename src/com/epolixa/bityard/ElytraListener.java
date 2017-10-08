package com.epolixa.bityard;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.material.MaterialData;

public class ElytraListener implements Listener
{
    private final Bityard bityard;

    public ElytraListener(Bityard bityard)
    {
        this.bityard = bityard;
    }

    @EventHandler
    public void onStartGlide(EntityToggleGlideEvent event)
    {
        try
        {
            if (event.getEntityType() == EntityType.PLAYER)
            {
                Player player = (Player)event.getEntity();
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, SoundCategory.PLAYERS, 0.4f, 2f);
            }
        }
        catch (Exception e)
        {
            bityard.log(e.toString());
        }
    }

    @EventHandler
    public void onSneakWhileGliding(PlayerToggleSneakEvent event)
    {
        try
        {
            Player player = event.getPlayer();
            if (player.isGliding())
            {
                player.setGliding(false);
                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERDRAGON_FLAP, SoundCategory.PLAYERS, 0.4f, 1.5f);
            }
        }
        catch (Exception e)
        {
            bityard.log(e.toString());
        }
    }
}
