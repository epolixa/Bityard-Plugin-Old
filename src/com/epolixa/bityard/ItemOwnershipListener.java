package com.epolixa.bityard;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemOwnershipListener implements Listener
{
    private final Bityard bityard;

    public ItemOwnershipListener(Bityard bityard)
    {
        this.bityard = bityard;
    }

    private ItemStack setItemsOwnership(ItemStack items, String ownerName)
    {
        ItemMeta itemsMeta;
        List<String> itemsLore;
        boolean hasOwner = false;

        if (items.hasItemMeta())
        {
            itemsMeta = items.getItemMeta();

            if (itemsMeta.hasLore())
            {
                itemsLore = itemsMeta.getLore();

                for (int i = 0; i < itemsLore.size(); i++)
                {
                    if (itemsLore.get(i).contains(ChatColor.RESET + "" + ChatColor.GRAY + "Owner: " + ChatColor.RESET))
                    {
                        hasOwner = true;
                        break;
                    }
                }
            }
            else
            {
                itemsLore = new ArrayList<String>();
            }
        }
        else
        {
            itemsMeta = bityard.getServer().getItemFactory().getItemMeta(items.getType());
            itemsLore = new ArrayList<String>();
        }

        if (!hasOwner)
        {
            itemsLore.add(ChatColor.RESET + "" + ChatColor.GRAY + "Owner: " + ChatColor.RESET + ownerName);
            itemsMeta.setLore(itemsLore);
            items.setItemMeta(itemsMeta);
        }

        return items;
    }

    @EventHandler
    public void onItemPickup(PlayerPickupItemEvent event)
    {
        try
        {
            event.getItem().setItemStack(
                    setItemsOwnership(
                            event.getItem().getItemStack(),
                            event.getPlayer().getDisplayName()
                    )
            );
        }
        catch (Exception e)
        {
            bityard.sendLog(e.toString());
        }
    }

    @EventHandler
    public void onPrepareItemCraft(PrepareItemCraftEvent event)
    {
        try
        {
            String name = event.getInventory().getViewers().get(0).getName();
            String ownerName = bityard.getServer().getScoreboardManager().getMainScoreboard().getEntryTeam(name).getPrefix() + name + ChatColor.RESET;

            event.getInventory().setResult(
                    setItemsOwnership(
                            event.getInventory().getResult(),
                            ownerName
                    )
            );
        }
        catch (Exception e)
        {
            bityard.sendLog(e.toString());
        }
    }
}
