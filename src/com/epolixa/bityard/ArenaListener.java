package com.epolixa.bityard;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ArenaListener implements Listener
{
    private final Bityard bityard;

    public ArenaListener(Bityard bityard)
    {
        this.bityard = bityard;
    }

    @EventHandler
    public void onArenaDeath(PlayerDeathEvent event)
    {
        try
        {
            Player player = event.getEntity();
            Location location  = player.getLocation();
            if
            (
                location.getBlockX() >= 545 && location.getBlockX() <= 567 &&
                location.getBlockY() >= 66  && location.getBlockY() <= 70 &&
                location.getBlockZ() >= 636 && location.getBlockZ() <= 658 &&
                player.getKiller() instanceof Player
            )
            {
                event.setKeepInventory(true);
                event.setKeepLevel(true);

                ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
                SkullMeta skullMeta = (SkullMeta)skull.getItemMeta();
                skullMeta.setOwner(player.getName());
                skull.setItemMeta(skullMeta);
                player.getWorld().dropItemNaturally(location, skull);
            }
        }
        catch (Exception e)
        {
            bityard.sendLog(e.toString());
        }
    }
}
