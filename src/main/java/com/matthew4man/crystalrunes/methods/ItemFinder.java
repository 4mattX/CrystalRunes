package com.matthew4man.crystalrunes.methods;

import com.matthew4man.crystalrunes.CrystalRunes;
import com.matthew4man.crystalrunes.objects.Crystal;
import me.kodysimpson.simpapi.heads.SkullCreator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class ItemFinder {

    public static ItemStack find(String itemName) {

        ItemStack itemStack = null;

        switch (itemName) {
            case "default-crystal":
                return makeItem(Material.SEA_LANTERN, ChatColor.WHITE + "Default-Crystal", ChatColor.GRAY + "Crystal");
            case "red-blue-crystal":
                return makeItem(Material.PURPLE_GLAZED_TERRACOTTA, ChatColor.LIGHT_PURPLE + "Red-Blue-Crystal", ChatColor.GRAY + "Crystal");
            case "red-green-crystal":
                return makeItem(Material.BLACK_GLAZED_TERRACOTTA, ChatColor.RED + "Red-Green-Crystal", ChatColor.GRAY + "Crystal");
            case "red-yellow-crystal":
                return makeItem(Material.ORANGE_GLAZED_TERRACOTTA, ChatColor.GOLD + "Red-Yellow-Crystal", ChatColor.GRAY + "Crystal");
            case "blue-green-crystal":
                return makeItem(Material.CYAN_GLAZED_TERRACOTTA, ChatColor.AQUA + "Blue-Green-Crystal", ChatColor.GRAY + "Crystal");
            case "green-yellow-crystal":
                return makeItem(Material.LIME_GLAZED_TERRACOTTA, ChatColor.GREEN + "Green-Yellow-Crystal", ChatColor.GRAY + "Crystal");
            case "blue-yellow-crystal":
                return makeItem(Material.LIGHT_BLUE_GLAZED_TERRACOTTA, ChatColor.BLUE + "Blue-Yellow-Crystal", ChatColor.GRAY + "Crystal");
            case "background-white":
                return makeItem(Material.WHITE_STAINED_GLASS_PANE, " ");
            case "background-gray":
                return makeItem(Material.GRAY_STAINED_GLASS_PANE, " ");
            case "background-black":
                return makeItem(Material.BLACK_STAINED_GLASS_PANE, " ");
            case "background-red":
                return makeItem(Material.RED_STAINED_GLASS_PANE, " ");
            case "background-pink":
                return makeItem(Material.PINK_STAINED_GLASS_PANE, " ");
            case "background-blue":
                return makeItem(Material.BLUE_STAINED_GLASS_PANE, " ");
            case "background-light-blue":
                return makeItem(Material.LIGHT_BLUE_STAINED_GLASS_PANE, " ");
            case "background-green":
                return makeItem(Material.GREEN_STAINED_GLASS_PANE, " ");
            case "background-lime":
                return makeItem(Material.LIME_STAINED_GLASS_PANE, " ");
            case "background-yellow":
                return makeItem(Material.YELLOW_STAINED_GLASS_PANE, " ");
            case "background-orange":
                return makeItem(Material.ORANGE_STAINED_GLASS_PANE, " ");
            case "background-magenta":
                return makeItem(Material.MAGENTA_STAINED_GLASS_PANE, " ");
            case "player-crystals":
                return makeItem(Material.GRASS_BLOCK, ChatColor.GREEN + "Your Crystals");
            case "friend-crystals":
                return makeItem(Material.WARPED_NYLIUM, ChatColor.AQUA + "Friend Crystals");
            case "public-crystals":
                return makeItem(Material.CRIMSON_NYLIUM, ChatColor.RED + "Public Crystals");
            case "runes":
                return makeItem(Material.AMETHYST_CLUSTER, ChatColor.LIGHT_PURPLE + "RUNES");
            case "settings":
                return makeItem(Material.COMPARATOR, ChatColor.WHITE + "Crystal Settings");
            case "break-button":
                return makeItem(Material.BARRIER, ChatColor.DARK_RED + "Break Crystal");
            case "gold":
                return makeItem(Material.RAW_GOLD, ChatColor.GOLD + "Gold");
            case "left-arrow":
                ItemStack head = SkullCreator.itemFromName("MHF_ArrowLeft");
                return makeItem(head, ChatColor.DARK_GRAY + "Back");
            case "back-friend-menu":
                ItemStack head2 = SkullCreator.itemFromName("MHF_ArrowLeft");
                return makeItem(head2, ChatColor.DARK_GRAY + "Back to Friends");
            case "friend-send":
                return makeItem(Material.EMERALD_BLOCK, ChatColor.GREEN + "Send Friend Request", ChatColor.GRAY + "Click to send request");
            case "unfriend-send":
                return makeItem(Material.REDSTONE_BLOCK, ChatColor.RED + "Remove Friend", ChatColor.GRAY + "Click to Unfriend");
            case "trade":
                return makeItem(Material.ENDER_CHEST, ChatColor.LIGHT_PURPLE + "Trade", ChatColor.GRAY + "Click to trade");
            case "anti-tnt":
                return makeItem(Material.TNT, ChatColor.RED + "Anti-TNT", ChatColor.GRAY + "TNT does not damage blocks");
            case "anti-creeper":
                return makeItem(Material.CREEPER_SPAWN_EGG, ChatColor.RED + "Anti-Creeper", ChatColor.GRAY + "Creepers do not damage blocks");
            case "anti-pvp":
                return makeItem(Material.DIAMOND_SWORD, ChatColor.RED + "Anti-PVP", ChatColor.GRAY + "Players cannot damage players");
            case "untrackable":
                return makeItem(Material.COMPASS, ChatColor.RED + "Un-Trackable", ChatColor.GRAY + "This Crystal cannot be tracked");
            case "store-legendary":
                return makeItem(Material.BEACON, ChatColor.RED + "Store Legendary Items", ChatColor.GRAY + "Ability to store legendary items");
            case "no-fall":
                return makeItem(Material.FEATHER, ChatColor.RED + "No Fall", ChatColor.GRAY + "No fall damage");
            case "increase-xp":
                return makeItem(Material.ENCHANTING_TABLE, ChatColor.BLUE + "Increase XP", ChatColor.GRAY + "Experience x2");
            case "increase-farm":
                return makeItem(Material.WHEAT, ChatColor.BLUE + "Increase Farm Drops", ChatColor.GRAY + "Farm Drops x2");
            case "increase-ore":
                return makeItem(Material.EMERALD_ORE, ChatColor.BLUE + "Increase Ore Drops", ChatColor.GRAY + "Ore Drops x2");
            case "increase-mob":
                return makeItem(Material.ROTTEN_FLESH, ChatColor.BLUE + "Increase Mob Drops", ChatColor.GRAY + "Mob Drops x2");
            case "gold-farm":
                return makeItem(Material.GOLD_NUGGET, ChatColor.BLUE + "Gold Drop from Farming", ChatColor.GRAY + "Harvesting crops drops gold");
            case "gold-mob":
                return makeItem(Material.RAW_GOLD, ChatColor.BLUE + "Gold Drop from Mobs", ChatColor.GRAY + "Killing mobs drops gold");
            case "crop-tick":
                return makeItem(Material.FARMLAND, ChatColor.GREEN + "Crop Tick Speed", ChatColor.GRAY + "Crops grow 2x fast");
            case "spawner-tick":
                return makeItem(Material.SPAWNER, ChatColor.GREEN + "Spawner Tick Speed", ChatColor.GRAY + "Spawners spawn 2x mobs");
            case "monster-health":
                return makeItem(Material.ZOMBIE_SPAWN_EGG, ChatColor.GREEN + "Monster Health 1", ChatColor.GRAY + "All monsters have 1 health");
            case "animal-health":
                return makeItem(Material.PIG_SPAWN_EGG, ChatColor.GREEN + "Animal Health 1", ChatColor.GRAY + "All animals have 1 health");
            case "smelt-time":
                return makeItem(Material.FURNACE, ChatColor.GREEN + "Furnace Tick Speed", ChatColor.GRAY + "Items smelt 2x fast");
            case "bottle-xp":
                return makeItem(Material.GLASS_BOTTLE, ChatColor.GREEN + "Bottle Experience", ChatColor.GRAY + "Bottle up existing experience");
            case "haste":
                return makeItem(Material.IRON_PICKAXE, ChatColor.YELLOW + "Haste", ChatColor.GRAY + "Acquire Haste");
            case "instant-gold":
                return makeItem(Material.GOLDEN_PICKAXE, ChatColor.YELLOW + "Instant Gold Mine", ChatColor.GRAY + "All gold tools instant mine");
            case "double-jump":
                return makeItem(Material.POTION, ChatColor.YELLOW + "Double Jump", ChatColor.GRAY + "Mario time");
            case "big-chunk":
                return makeItem(Material.GRASS_BLOCK, ChatColor.YELLOW + "Big Claim", ChatColor.GRAY + "Crystal claims 5x5 chunk area");
            case "cobble-gen":
                return makeItem(Material.COBBLESTONE, ChatColor.YELLOW + "Cobble Ore Generator", ChatColor.GRAY + "Cobblestone generators create ore as well");
            case "use-spawner":
                return makeItem(Material.SPAWNER, ChatColor.YELLOW + "Use Spawner", ChatColor.GRAY + "Spawners spawn mobs");
            case "red-rune-primary":
                return makeItem(Material.RED_CANDLE, ChatColor.RED + "Red Primary", ChatColor.GRAY + "Make Crystal Primary Rune Red");
            case "blue-rune-primary":
                return makeItem(Material.BLUE_CANDLE, ChatColor.BLUE + "Blue Primary", ChatColor.GRAY + "Make Crystal Primary Rune Blue");
            case "green-rune-primary":
                return makeItem(Material.GREEN_CANDLE, ChatColor.GREEN + "Green Primary", ChatColor.GRAY + "Make Crystal Primary Rune Green");
            case "yellow-rune-primary":
                return makeItem(Material.YELLOW_CANDLE, ChatColor.YELLOW + "Yellow Primary", ChatColor.GRAY + "Make Crystal Primary Rune Yellow");
            case "red-rune-secondary":
                return makeItem(Material.RED_CANDLE, ChatColor.RED + "Red Secondary", ChatColor.GRAY + "Make Crystal Secondary Rune Red");
            case "blue-rune-secondary":
                return makeItem(Material.BLUE_CANDLE, ChatColor.BLUE + "Blue Secondary", ChatColor.GRAY + "Make Crystal Secondary Rune Blue");
            case "green-rune-secondary":
                return makeItem(Material.GREEN_CANDLE, ChatColor.GREEN + "Green Secondary", ChatColor.GRAY + "Make Crystal Secondary Rune Green");
            case "yellow-rune-secondary":
                return makeItem(Material.YELLOW_CANDLE, ChatColor.YELLOW + "Yellow Secondary", ChatColor.GRAY + "Make Crystal Secondary Rune Yellow");
            case "decline-crystal-break":
                return makeItem(Material.REDSTONE_BLOCK, ChatColor.RED + "Do Not Break", ChatColor.GRAY + "Decline Breaking Crystal");
            case "accept-crystal-break":
                return makeItem(Material.EMERALD_BLOCK, ChatColor.GREEN + "Yes, Break", ChatColor.GRAY + "Accept Breaking Crystal");
            case "tp-friend-crystal":
                return makeItem(Material.SEA_LANTERN, ChatColor.BLUE + "Friend's Crystals", ChatColor.GRAY + "List Friend's Crystals");
            case "building-setting-green":
                return makeItem(Material.LIME_SHULKER_BOX, ChatColor.WHITE + "Building Privileges:", ChatColor.GREEN + "You" + ChatColor.GRAY + "/Friends/Everyone");
            case "building-setting-yellow":
                return makeItem(Material.YELLOW_SHULKER_BOX, ChatColor.WHITE + "Building Privileges:", ChatColor.GRAY + "You/" + ChatColor.GREEN + "Friends" + ChatColor.GRAY + "/Everyone");
            case "building-setting-red":
                return makeItem(Material.RED_SHULKER_BOX, ChatColor.WHITE + "Building Privileges:", ChatColor.GRAY + "You/Friends/" + ChatColor.GREEN + "Everyone");
            case "teleport-setting-green":
                return makeItem(Material.LIME_SHULKER_BOX, ChatColor.WHITE + "Teleport Privileges:", ChatColor.GREEN + "You" + ChatColor.GRAY + "/Friends/Public");
            case "teleport-setting-yellow":
                return makeItem(Material.YELLOW_SHULKER_BOX, ChatColor.WHITE + "Teleport Privileges:", ChatColor.GRAY + "You/" + ChatColor.GREEN + "Friends" + ChatColor.GRAY + "/Public");
            case "teleport-setting-red":
                return makeItem(Material.RED_SHULKER_BOX, ChatColor.WHITE + "Teleport Privileges:", ChatColor.GRAY + "You/Friends/" + ChatColor.GREEN + "Public");
            case "change-name":
                return makeItem(Material.NAME_TAG, ChatColor.GOLD + "Change Name", ChatColor.GRAY + "Click to change Crystal Name");
            case "accept-name-change":
                return makeItem(Material.EMERALD_BLOCK, ChatColor.GREEN + "Accept", ChatColor.GRAY + "Click to accept name change");
            case "decline-name-change":
                return makeItem(Material.EMERALD_BLOCK, ChatColor.GREEN + "Decline", ChatColor.GRAY + "Click to decline name change");
            case "help-book":
                return makeItem(Material.BOOK, ChatColor.GOLD + "Help", ChatColor.GRAY + "Click for rules, tips, and other help");
        }

        return null;
    }

    public static ItemStack find(String itemName, String playerUUID, String crystalUUID) {

        Crystal crystal = CrystalRunes.getCrystal(playerUUID, crystalUUID);
        String itemLore = crystal.getName();

        ItemStack itemButton = null;

        switch (itemName) {
            case "default-teleport":
                itemButton = makeItem(Material.SEA_LANTERN, ChatColor.WHITE + "Click to Teleport", ChatColor.GRAY + "❖" + itemLore);
                break;
            case "red-blue-teleport":
                itemButton = makeItem(Material.PURPLE_GLAZED_TERRACOTTA, ChatColor.LIGHT_PURPLE + "Click to Teleport", ChatColor.GRAY + "❖" + itemLore);
                break;
            case "red-green-teleport":
                itemButton = makeItem(Material.BLACK_GLAZED_TERRACOTTA, ChatColor.RED + "Click to Teleport", ChatColor.GRAY + "❖" + itemLore);
                break;
            case "red-yellow-teleport":
                itemButton = makeItem(Material.ORANGE_GLAZED_TERRACOTTA, ChatColor.GOLD + "Click to Teleport", ChatColor.GRAY + "❖" + itemLore);
                break;
            case "blue-green-teleport":
                itemButton = makeItem(Material.CYAN_GLAZED_TERRACOTTA, ChatColor.AQUA + "Click to Teleport", ChatColor.GRAY + "❖" + itemLore);
                break;
            case "blue-yellow-teleport":
                itemButton = makeItem(Material.LIGHT_BLUE_GLAZED_TERRACOTTA, ChatColor.AQUA + "Click to Teleport", ChatColor.GRAY + "❖" + itemLore);
                break;
            case "green-yellow-teleport":
                itemButton = makeItem(Material.LIME_GLAZED_TERRACOTTA, ChatColor.GREEN + "Click to Teleport", ChatColor.GRAY + "❖" + itemLore);
                break;
            case "spawn-teleport":
                itemButton = makeItem(Material.BUBBLE_CORAL_BLOCK, ChatColor.WHITE + "Click to Teleport", ChatColor.GRAY + "❖" + itemLore);
                break;
            case "pvp-teleport":
                itemButton = makeItem(Material.FIRE_CORAL_BLOCK, ChatColor.WHITE + "Click to Teleport", ChatColor.GRAY + "❖" + itemLore);
                break;
            case "shop-teleport":
                itemButton = makeItem(Material.TUBE_CORAL_BLOCK, ChatColor.WHITE + "Click to Teleport", ChatColor.GRAY + "❖" + itemLore);
                break;
        }

        ItemMeta itemMeta = itemButton.getItemMeta();
        PersistentDataContainer crystalItemData = itemMeta.getPersistentDataContainer();
        crystalItemData.set(new NamespacedKey(CrystalRunes.getPlugin(), "crystalUUID"), PersistentDataType.STRING, crystalUUID);
        itemButton.setItemMeta(itemMeta);

        return itemButton;
    }

    public static ItemStack makeItem(Material material, String itemName, String itemLore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();

        List<String> lore = new ArrayList<>();
        lore.add(itemLore);

        itemMeta.setLore(lore);
        itemMeta.setDisplayName(itemName);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack makeItem(ItemStack itemStack, String itemName) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(itemName);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static ItemStack makeItem(Material material, String itemName) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(itemName);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }


}
