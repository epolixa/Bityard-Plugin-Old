package com.epolixa.bityard;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;

import java.util.HashMap;

public class InventoryListener implements Listener
{
    private final Bityard bityard;

    private static HashMap<Material, Sound> materialSounds;

    public InventoryListener(Bityard bityard)
    {
        this.bityard = bityard;
        materialSounds = new HashMap<Material, Sound>();

        /* ##### BLOCKS ##### */
        materialSounds.put(Material.STONE, Sound.BLOCK_STONE_HIT);
        materialSounds.put(Material.GRASS, Sound.BLOCK_GRASS_HIT);
        materialSounds.put(Material.DIRT, Sound.BLOCK_GRAVEL_HIT);
        materialSounds.put(Material.COBBLESTONE, Sound.BLOCK_STONE_HIT);
        materialSounds.put(Material.WOOD, Sound.BLOCK_WOOD_HIT);
        materialSounds.put(Material.SAPLING, Sound.BLOCK_GRASS_HIT);
        materialSounds.put(Material.SAND, Sound.BLOCK_SAND_HIT);
        materialSounds.put(Material.GRAVEL, Sound.BLOCK_GRAVEL_HIT);
        materialSounds.put(Material.GOLD_ORE, Sound.BLOCK_STONE_HIT);
        materialSounds.put(Material.IRON_ORE, Sound.BLOCK_STONE_HIT);
        materialSounds.put(Material.COAL_ORE, Sound.BLOCK_STONE_HIT);
        materialSounds.put(Material.LOG, Sound.BLOCK_WOOD_HIT);
        materialSounds.put(Material.LEAVES, Sound.BLOCK_GRASS_HIT);
        materialSounds.put(Material.WOOL, Sound.BLOCK_CLOTH_HIT);
        materialSounds.put(Material.GLASS, Sound.BLOCK_GLASS_HIT);
        materialSounds.put(Material.LAPIS_ORE, Sound.BLOCK_STONE_HIT);
        materialSounds.put(Material.LAPIS_BLOCK, Sound.BLOCK_STONE_HIT);
        materialSounds.put(Material.DISPENSER, Sound.BLOCK_STONE_HIT);
        materialSounds.put(Material.SANDSTONE, Sound.BLOCK_STONE_HIT);
        materialSounds.put(Material.NOTE_BLOCK, Sound.BLOCK_WOOD_HIT);
        materialSounds.put(Material.BED, Sound.BLOCK_CLOTH_HIT);
        materialSounds.put(Material.POWERED_RAIL, Sound.BLOCK_ANVIL_HIT);
        materialSounds.put(Material.DETECTOR_RAIL, Sound.BLOCK_ANVIL_HIT);
        materialSounds.put(Material.PISTON_STICKY_BASE, Sound.BLOCK_STONE_HIT);
        materialSounds.put(Material.WEB, Sound.BLOCK_SLIME_HIT);
        materialSounds.put(Material.LONG_GRASS, Sound.BLOCK_GRASS_HIT);
        materialSounds.put(Material.STAINED_CLAY, Sound.BLOCK_STONE_HIT);

        /* ##### ITEMS ##### */
        materialSounds.put(Material.IRON_SPADE, Sound.ITEM_ARMOR_EQUIP_IRON);
        materialSounds.put(Material.IRON_PICKAXE, Sound.ITEM_ARMOR_EQUIP_IRON);
        materialSounds.put(Material.IRON_AXE, Sound.ITEM_ARMOR_EQUIP_IRON);
        materialSounds.put(Material.FLINT_AND_STEEL, Sound.ITEM_ARMOR_EQUIP_IRON);
        materialSounds.put(Material.APPLE, Sound.BLOCK_GRASS_HIT);
        materialSounds.put(Material.BOW, Sound.ENTITY_ARMORSTAND_HIT);
        materialSounds.put(Material.ARROW, Sound.BLOCK_NOTE_HAT);
        materialSounds.put(Material.COAL, Sound.BLOCK_STONE_HIT);
        materialSounds.put(Material.DIAMOND, Sound.BLOCK_ANVIL_HIT);
        materialSounds.put(Material.IRON_INGOT, Sound.BLOCK_ANVIL_HIT);
        materialSounds.put(Material.GOLD_INGOT, Sound.BLOCK_ANVIL_HIT);
        materialSounds.put(Material.IRON_SWORD, Sound.ITEM_ARMOR_EQUIP_IRON);
        materialSounds.put(Material.WOOD_SWORD, Sound.ENTITY_ARMORSTAND_HIT);
        materialSounds.put(Material.WOOD_SPADE, Sound.ENTITY_ARMORSTAND_HIT);
        materialSounds.put(Material.WOOD_PICKAXE, Sound.ENTITY_ARMORSTAND_HIT);
        materialSounds.put(Material.WOOD_AXE, Sound.ENTITY_ARMORSTAND_HIT);
        materialSounds.put(Material.STONE_SWORD, Sound.BLOCK_STONE_HIT);
        materialSounds.put(Material.STONE_SPADE, Sound.BLOCK_STONE_HIT);
        materialSounds.put(Material.STONE_PICKAXE, Sound.BLOCK_STONE_HIT);
        materialSounds.put(Material.STONE_AXE, Sound.BLOCK_STONE_HIT);
        materialSounds.put(Material.DIAMOND_SWORD, Sound.ITEM_ARMOR_EQUIP_DIAMOND);
        materialSounds.put(Material.DIAMOND_SPADE, Sound.ITEM_ARMOR_EQUIP_DIAMOND);
        materialSounds.put(Material.DIAMOND_PICKAXE, Sound.ITEM_ARMOR_EQUIP_DIAMOND);
        materialSounds.put(Material.DIAMOND_AXE, Sound.ITEM_ARMOR_EQUIP_DIAMOND);
        materialSounds.put(Material.STICK, Sound.BLOCK_NOTE_HAT);
        materialSounds.put(Material.BOWL, Sound.ENTITY_ARMORSTAND_HIT);
        materialSounds.put(Material.MUSHROOM_SOUP, Sound.ITEM_BUCKET_EMPTY);
        materialSounds.put(Material.GOLD_SWORD, Sound.ITEM_ARMOR_EQUIP_GOLD);
        materialSounds.put(Material.GOLD_SPADE, Sound.ITEM_ARMOR_EQUIP_GOLD);
        materialSounds.put(Material.GOLD_PICKAXE, Sound.ITEM_ARMOR_EQUIP_GOLD);
        materialSounds.put(Material.GOLD_AXE, Sound.ITEM_ARMOR_EQUIP_GOLD);
        materialSounds.put(Material.STRING, Sound.ENTITY_LEASHKNOT_PLACE);
        materialSounds.put(Material.CLAY_BALL, Sound.BLOCK_GRAVEL_HIT);
    }

    @EventHandler
    public void onMoveInventoryItem(InventoryClickEvent event)
    {
        try
        {
            Player player = (Player)event.getInventory().getViewers().get(0);
            if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) // current item in clicked slot
            {
                player.playSound(player.getLocation(), getSoundFromMaterial(event.getCurrentItem().getType()), SoundCategory.PLAYERS, 0.4f, 1.2f);
            }
            if (event.getCursor() != null && event.getCursor().getType() != Material.AIR) // current item in cursor
            {
                player.playSound(player.getLocation(), getSoundFromMaterial(event.getCursor().getType()), SoundCategory.PLAYERS, 0.4f, 0.8f);
            }
        }
        catch (Exception e)
        {
            bityard.log(e.toString());
        }
    }

    private Sound getSoundFromMaterial(Material material)
    {
        Sound sound = materialSounds.get(material);
        return sound == null ? Sound.ENTITY_ITEM_PICKUP : sound;
    }
}
