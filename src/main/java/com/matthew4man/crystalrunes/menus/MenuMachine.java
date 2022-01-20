package com.matthew4man.crystalrunes.menus;

import com.matthew4man.crystalrunes.CrystalRunes;
import com.matthew4man.crystalrunes.methods.CrystalFinder;
import com.matthew4man.crystalrunes.methods.ItemFinder;
import com.matthew4man.crystalrunes.objects.Crystal;
import com.matthew4man.crystalrunes.objects.Teleporter;
import me.kodysimpson.simpapi.heads.SkullCreator;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Skull;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MenuMachine {

    public static void buttonUse(Player player, String buttonName) {

        Entity crystal = null;

        for (Entity entity : player.getNearbyEntities(6, 6, 6)) {
            if (entity.getType() == EntityType.ENDER_CRYSTAL) {
                crystal = entity;
            }
        }

        String crystalUUID = "";

        try {
            PersistentDataContainer crystalData = crystal.getPersistentDataContainer();
            crystalUUID = crystalData.get(new NamespacedKey(CrystalRunes.getPlugin(), "crystalUUID"), PersistentDataType.STRING);
        } catch (Exception e) {
        }

        if (buttonName.contains("player-crystals")) {
            openMenu(player, "player-crystals-menu", crystalUUID);
        }

        if (buttonName.contains("crystal-menu")) {
            openMenu(player, "crystal-main-menu", crystalUUID);
        }

        if (buttonName.contains("crystal-runes")) {
            openMenu(player, "crystal-runes-menu", crystalUUID);
        }

        if (buttonName.contains("crystal-break")) {
            openMenu(player, "crystal-break-menu", crystalUUID);
        }

        if (buttonName.contains("send-friend-request")) {
            String friendUUID = "";
            PersistentDataContainer playerData = player.getPersistentDataContainer();
            friendUUID = playerData.get(new NamespacedKey(CrystalRunes.getPlugin(), "playerSelectUUID"), PersistentDataType.STRING);
            Player friend = Bukkit.getPlayer(UUID.fromString(friendUUID));
            sendInvite(player, friend);
        }

        if (buttonName.contains("remove-friend")) {
            String friendUUID = "";
            PersistentDataContainer playerData = player.getPersistentDataContainer();
            friendUUID = playerData.get(new NamespacedKey(CrystalRunes.getPlugin(), "playerSelectUUID"), PersistentDataType.STRING);
            Player friend = Bukkit.getPlayer(UUID.fromString(friendUUID));
            unFriend(player, friend);
        }
    }

    public static void buttonTeleport(Player player, String crystalUUID) {

        Location location = CrystalRunes.getCrystal(crystalUUID).getLocation();
        location.setWorld(CrystalRunes.getCrystal(crystalUUID).getWorld());
        player.closeInventory();

        Teleporter teleporter = new Teleporter(player, location);
        teleporter.teleportEffect();
        teleporter.teleport();
    }

    public static void openMenu(Player player, String menuName, String crystalUUID) {

        switch (menuName) {
            case "crystal-main-menu":
                player.openInventory(crystalMainMenu(player, crystalUUID));
                break;
            case "crystal-runes-menu":
                int primary = CrystalFinder.findCrystal(player).getTypePrimary();
                int secondary = CrystalFinder.findCrystal(player).getTypeSecondary();
                player.openInventory(runeCreateMenu(player, crystalUUID, primary, secondary));
                break;
            case "player-crystals-menu":
                boolean inCrystal = false;
                if (!crystalUUID.isEmpty()) {
                    inCrystal = true;
                }
                player.openInventory(playerCrystalsMenu(player, inCrystal));
                break;
            case "public-crystals-menu":
                boolean inCrystal3 = false;
                if (!crystalUUID.isEmpty()) {
                    inCrystal3 = true;
                }
                player.openInventory(publicCrystalsMenu(player, inCrystal3));
                break;
            case "friend-menu":
                boolean inCrystal2 = false;
                if (!crystalUUID.isEmpty()) {
                    inCrystal2 = true;
                }
                player.openInventory(friendMenu(player, inCrystal2));
                break;

            case "crystal-break-menu":
                player.openInventory(breakCrystalMenu());
                break;

            case "crystal-settings-menu":
                player.openInventory(crystalSettings(player, crystalUUID));
                break;

            case "server-crystal-menu":
                player.openInventory(serverCrystalsMenu(player, crystalUUID));
                break;
        }
    }

    public static void openMenu(Player player, String friendUUID, String friendName, String menuName, String crystalUUID) {
        switch (menuName) {
            case "specific-friend-menu":
                boolean inCrystal = false;
                if (!crystalUUID.isEmpty()) {
                    inCrystal = true;
                }
                player.openInventory(specificFriendMenu(friendUUID, friendName, inCrystal));
                break;
            case "friend-crystal-menu":
                boolean inCrystal2 = false;
                if (!crystalUUID.isEmpty()) {
                    inCrystal2 = true;
                }
                player.openInventory(friendCrystalsMenu(friendUUID, friendName, inCrystal2));
                break;
        }
    }

    public static void openMenu(Player player, String menuName, Player friend) {

        switch (menuName) {
            case "friend-menu":
                player.openInventory(friendRequestMenu(player, friend));
                break;
        }

    }

    private static Inventory crystalMainMenu(Player player, String crystalUUID) {
        int amtSlots = 45;

        String crystalName = CrystalRunes.getCrystal(crystalUUID).getName();

        Inventory inventory = Bukkit.createInventory(null, amtSlots, "❖ " + crystalName);
        Crystal crystal = CrystalFinder.findCrystal(player);

        ItemStack background1 = null;
        ItemStack background2 = null;
        int pRune = crystal.getTypePrimary();
        int sRune = crystal.getTypeSecondary();

        switch (pRune) {
            case 0:
                background1 = ItemFinder.find("background-gray");
                break;
            case 1:
                background1 = ItemFinder.find("background-red");
                break;
            case 2:
                background1 = ItemFinder.find("background-blue");
                break;
            case 3:
                background1 = ItemFinder.find("background-lime");
                break;
            case 4:
                background1 = ItemFinder.find("background-yellow");
                break;
        }

        switch (sRune) {
            case 0:
                background2 = ItemFinder.find("background-black");
                break;
            case 1:
                background2 = ItemFinder.find("background-red");
                break;
            case 2:
                background2 = ItemFinder.find("background-blue");
                break;
            case 3:
                background2 = ItemFinder.find("background-lime");
                break;
            case 4:
                background2 = ItemFinder.find("background-yellow");
                break;
        }

        // Sets the background of menu
        for (int i = 0; i < amtSlots; i++) {
            if (i % 2 == 0) {
                inventory.setItem(i, background1);
                continue;
            }
            inventory.setItem(i, background2);
        }

        inventory.setItem(4, ItemFinder.find("player-crystals"));
        inventory.setItem(11, ItemFinder.find("friend-crystals"));
        inventory.setItem(15, ItemFinder.find("public-crystals"));
        inventory.setItem(22, ItemFinder.find("runes"));
        inventory.setItem(29, ItemFinder.find("settings"));
        inventory.setItem(33, ItemFinder.find("break-button"));
        inventory.setItem(40, ItemFinder.find("gold"));

        return inventory;
    }

    private static Inventory serverCrystalsMenu(Player player, String crystalUUID) {
        int amtSlots = 45;

        String crystalName = CrystalRunes.getCrystal(crystalUUID).getName();

        Inventory inventory = Bukkit.createInventory(null, amtSlots, "❖ " + crystalName);

        for (int i = 0; i < amtSlots; i++) {
            if (i % 2 == 0) {
                inventory.setItem(i, ItemFinder.find("background-magenta"));
                continue;
            }
            inventory.setItem(i, ItemFinder.find("background-lime"));
        }

        inventory.setItem(4, ItemFinder.find("player-crystals"));
        inventory.setItem(20, ItemFinder.find("friend-crystals"));
        inventory.setItem(22, ItemFinder.makeItem(Material.AMETHYST_CLUSTER, ChatColor.LIGHT_PURPLE + crystalName));
        inventory.setItem(24, ItemFinder.find("public-crystals"));
        inventory.setItem(40, ItemFinder.find("help-book"));

        return inventory;
    }

    private static Inventory playerCrystalsMenu(Player player, Boolean inCrystal) {
        int amtSlots;

        if (inCrystal) {
            amtSlots = 54;
        } else {
            amtSlots = 45;
        }

        Inventory inventory = Bukkit.createInventory(null, amtSlots, "❖ " + player.getName() + "'s Crystals");

        if (inCrystal) {
            for (int i = amtSlots - 9; i < amtSlots; i++) {
                inventory.setItem(i, ItemFinder.find("background-white"));
            }
        }


        // Load each crystal
        List<Crystal> crystalList = CrystalRunes.getCrystalMap().get(player.getUniqueId().toString());

        if (crystalList != null) {
            for (int i = 0; i < crystalList.size(); i++) {
                ItemStack crystalItem = null;
                Crystal crystal = crystalList.get(i);


                if (crystalList.get(i).getTypePrimary() == 1) {
                    if (crystalList.get(i).getTypeSecondary() == 0) {
                        crystalItem = ItemFinder.find("default-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 2) {
                        crystalItem = ItemFinder.find("red-blue-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 3) {
                        crystalItem = ItemFinder.find("red-green-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 4) {
                        crystalItem = ItemFinder.find("red-yellow-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    }
                }

                else if (crystalList.get(i).getTypePrimary() == 2) {
                    if (crystalList.get(i).getTypeSecondary() == 0) {
                        crystalItem = ItemFinder.find("default-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 1) {
                        crystalItem = ItemFinder.find("red-blue-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 3) {
                        crystalItem = ItemFinder.find("blue-green-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 4) {
                        crystalItem = ItemFinder.find("blue-yellow-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    }
                }

                else if (crystalList.get(i).getTypePrimary() == 3) {
                    if (crystalList.get(i).getTypeSecondary() == 0) {
                        crystalItem = ItemFinder.find("default-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 1) {
                        crystalItem = ItemFinder.find("red-green-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 2) {
                        crystalItem = ItemFinder.find("blue-green-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 4) {
                        crystalItem = ItemFinder.find("green-yellow-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    }
                } else if (crystalList.get(i).getTypePrimary() == 4) {
                    if (crystalList.get(i).getTypeSecondary() == 0) {
                        crystalItem = ItemFinder.find("default-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 1) {
                        crystalItem = ItemFinder.find("red-yellow-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 2) {
                        crystalItem = ItemFinder.find("blue-yellow-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 3) {
                        crystalItem = ItemFinder.find("green-yellow-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    }
                } else {
                    crystalItem = ItemFinder.find("default-teleport", player.getUniqueId().toString(), crystal.getUuid());
                }

                int x = crystalList.get(i).getX();
                int y = crystalList.get(i).getY();
                int z = crystalList.get(i).getZ();

                PersistentDataContainer crystalItemData = crystalItem.getItemMeta().getPersistentDataContainer();
                crystalItemData.set(new NamespacedKey(CrystalRunes.getPlugin(), "x"), PersistentDataType.INTEGER, x);
                crystalItemData.set(new NamespacedKey(CrystalRunes.getPlugin(), "y"), PersistentDataType.INTEGER, y);
                crystalItemData.set(new NamespacedKey(CrystalRunes.getPlugin(), "z"), PersistentDataType.INTEGER, z);

                inventory.setItem(i, crystalItem);
            }
        }

        if (inCrystal) {
            inventory.setItem(45, ItemFinder.find("left-arrow"));
        }
        return inventory;
    }

    private static Inventory publicCrystalsMenu(Player player, Boolean inCrystal) {
        int amtSlots;

        if (inCrystal) {
            amtSlots = 54;
        } else {
            amtSlots = 45;
        }

        Inventory inventory = Bukkit.createInventory(null, amtSlots, "❖ " + player.getName() + "'s Crystals");

        if (inCrystal) {
            for (int i = amtSlots - 9; i < amtSlots; i++) {
                inventory.setItem(i, ItemFinder.find("background-white"));
            }
        }


        // Load each crystal
        List<Crystal> crystalList = new ArrayList<>();

        for (List<Crystal> allCrystalList : CrystalRunes.getCrystalMap().values()) {

            allCrystalList.forEach(specificCrystal -> {

                if (specificCrystal.getTpStatus() == 2) {
                    crystalList.add(specificCrystal);
                }

            });
        }

        int serverIndex = 0;

        // Server Crystals
        if (crystalList != null) {
            for (int i = 0; i < crystalList.size(); i++) {
                ItemStack crystalItem = null;
                Crystal crystal = crystalList.get(i);

                if (crystalList.get(i).getTypePrimary() == 5) {
                    if (crystalList.get(i).getName().contains("Spawn")) {
                        crystalItem = ItemFinder.find("spawn-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    } else if (crystalList.get(i).getName().contains("Pvp")) {
                        crystalItem = ItemFinder.find("pvp-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    } else if (crystalList.get(i).getName().contains("Shop")) {
                        crystalItem = ItemFinder.find("shop-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    } else {
                        crystalItem = ItemFinder.find("default-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    }
                    inventory.setItem(serverIndex, crystalItem);
                    serverIndex++;
                }

            }
        }

        // Create line between server public crystals and player public crystals

        for (int i = 9; i < 18; i++) {
            inventory.setItem(i, ItemFinder.find("background-white"));
        }

        int index = 18;

        if (crystalList != null) {
            for (int i = 0; i < crystalList.size(); i++) {
                ItemStack crystalItem = null;
                Crystal crystal = crystalList.get(i);

                // If server specific then skip
                if (crystalList.get(i).getTypePrimary() == 5) {
                    continue;
                }

                if (crystalList.get(i).getTypePrimary() == 1) {
                    if (crystalList.get(i).getTypeSecondary() == 0) {
                        crystalItem = ItemFinder.find("default-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 2) {
                        crystalItem = ItemFinder.find("red-blue-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 3) {
                        crystalItem = ItemFinder.find("red-green-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 4) {
                        crystalItem = ItemFinder.find("red-yellow-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    }
                }

                else if (crystalList.get(i).getTypePrimary() == 2) {
                    if (crystalList.get(i).getTypeSecondary() == 0) {
                        crystalItem = ItemFinder.find("default-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 1) {
                        crystalItem = ItemFinder.find("red-blue-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 3) {
                        crystalItem = ItemFinder.find("blue-green-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 4) {
                        crystalItem = ItemFinder.find("blue-yellow-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    }
                }

                else if (crystalList.get(i).getTypePrimary() == 3) {
                    if (crystalList.get(i).getTypeSecondary() == 0) {
                        crystalItem = ItemFinder.find("default-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 1) {
                        crystalItem = ItemFinder.find("red-green-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 2) {
                        crystalItem = ItemFinder.find("blue-green-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 4) {
                        crystalItem = ItemFinder.find("green-yellow-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    }
                } else if (crystalList.get(i).getTypePrimary() == 4) {
                    if (crystalList.get(i).getTypeSecondary() == 0) {
                        crystalItem = ItemFinder.find("default-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 1) {
                        crystalItem = ItemFinder.find("red-yellow-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 2) {
                        crystalItem = ItemFinder.find("blue-yellow-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 3) {
                        crystalItem = ItemFinder.find("green-yellow-teleport", player.getUniqueId().toString(), crystal.getUuid());
                    }
                } else {
                    crystalItem = ItemFinder.find("default-teleport", player.getUniqueId().toString(), crystal.getUuid());
                }

                int x = crystalList.get(i).getX();
                int y = crystalList.get(i).getY();
                int z = crystalList.get(i).getZ();

                PersistentDataContainer crystalItemData = crystalItem.getItemMeta().getPersistentDataContainer();
                crystalItemData.set(new NamespacedKey(CrystalRunes.getPlugin(), "x"), PersistentDataType.INTEGER, x);
                crystalItemData.set(new NamespacedKey(CrystalRunes.getPlugin(), "y"), PersistentDataType.INTEGER, y);
                crystalItemData.set(new NamespacedKey(CrystalRunes.getPlugin(), "z"), PersistentDataType.INTEGER, z);

                ItemMeta crystalItemMeta = crystalItem.getItemMeta();
                List<String> crystalLore = crystalItemMeta.getLore();
                crystalLore.add(ChatColor.WHITE + crystal.getOwnerName());
                crystalItemMeta.setLore(crystalLore);
                crystalItem.setItemMeta(crystalItemMeta);


                inventory.setItem(index, crystalItem);
                index++;
            }
        }

        if (inCrystal) {
            inventory.setItem(45, ItemFinder.find("left-arrow"));
        }
        return inventory;
    }

    private static Inventory friendCrystalsMenu(String playerUUID, String playerName, Boolean inCrystal) {
        int amtSlots;

        if (inCrystal) {
            amtSlots = 54;
        } else {
            amtSlots = 45;
        }

        Inventory inventory = Bukkit.createInventory(null, amtSlots, "❖ " + playerName + "'s Crystals");

        if (inCrystal) {
            for (int i = amtSlots - 9; i < amtSlots; i++) {
                inventory.setItem(i, ItemFinder.find("background-white"));
            }
        }


        // Load each crystal
        List<Crystal> crystalList = CrystalRunes.getCrystalMap().get(playerUUID);

        if (crystalList != null) {
            for (int i = 0; i < crystalList.size(); i++) {
                ItemStack crystalItem = null;
                Crystal crystal = crystalList.get(i);


                if (crystalList.get(i).getTypePrimary() == 1) {
                    if (crystalList.get(i).getTypeSecondary() == 0) {
                        crystalItem = ItemFinder.find("default-teleport", playerUUID, crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 2) {
                        crystalItem = ItemFinder.find("red-blue-teleport", playerUUID, crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 3) {
                        crystalItem = ItemFinder.find("red-green-teleport", playerUUID, crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 4) {
                        crystalItem = ItemFinder.find("red-yellow-teleport", playerUUID, crystal.getUuid());
                    }
                }

                else if (crystalList.get(i).getTypePrimary() == 2) {
                    if (crystalList.get(i).getTypeSecondary() == 0) {
                        crystalItem = ItemFinder.find("default-teleport", playerUUID, crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 1) {
                        crystalItem = ItemFinder.find("red-blue-teleport", playerUUID, crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 3) {
                        crystalItem = ItemFinder.find("blue-green-teleport", playerUUID, crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 4) {
                        crystalItem = ItemFinder.find("blue-yellow-teleport", playerUUID, crystal.getUuid());
                    }
                }

                else if (crystalList.get(i).getTypePrimary() == 3) {
                    if (crystalList.get(i).getTypeSecondary() == 0) {
                        crystalItem = ItemFinder.find("default-teleport", playerUUID, crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 1) {
                        crystalItem = ItemFinder.find("red-green-teleport", playerUUID, crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 2) {
                        crystalItem = ItemFinder.find("blue-green-teleport", playerUUID, crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 4) {
                        crystalItem = ItemFinder.find("green-yellow-teleport", playerUUID, crystal.getUuid());
                    }
                } else if (crystalList.get(i).getTypePrimary() == 4) {
                    if (crystalList.get(i).getTypeSecondary() == 0) {
                        crystalItem = ItemFinder.find("default-teleport", playerUUID, crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 1) {
                        crystalItem = ItemFinder.find("red-yellow-teleport", playerUUID, crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 2) {
                        crystalItem = ItemFinder.find("blue-yellow-teleport", playerUUID, crystal.getUuid());
                    } else if (crystalList.get(i).getTypeSecondary() == 3) {
                        crystalItem = ItemFinder.find("green-yellow-teleport", playerUUID, crystal.getUuid());
                    }
                } else {
                    crystalItem = ItemFinder.find("default-teleport", playerUUID, crystal.getUuid());
                }

                int x = crystalList.get(i).getX();
                int y = crystalList.get(i).getY();
                int z = crystalList.get(i).getZ();

                PersistentDataContainer crystalItemData = crystalItem.getItemMeta().getPersistentDataContainer();
                crystalItemData.set(new NamespacedKey(CrystalRunes.getPlugin(), "x"), PersistentDataType.INTEGER, x);
                crystalItemData.set(new NamespacedKey(CrystalRunes.getPlugin(), "y"), PersistentDataType.INTEGER, y);
                crystalItemData.set(new NamespacedKey(CrystalRunes.getPlugin(), "z"), PersistentDataType.INTEGER, z);

                inventory.setItem(i, crystalItem);
            }
        }

        if (inCrystal) {
            inventory.setItem(45, ItemFinder.find("left-arrow"));
        }
        return inventory;
    }

    private static Inventory friendRequestMenu(Player player, Player friend) {
        int amtSlots = 9;
        Inventory inventory = Bukkit.createInventory(null, amtSlots, ChatColor.AQUA + "-=❖ " + ChatColor.LIGHT_PURPLE + friend.getName() + ChatColor.AQUA + " ❖=-");

        for (int i = 0; i < amtSlots; i++) {
            inventory.setItem(i, ItemFinder.find("background-white"));
        }

        // Adding trading button
        inventory.setItem(6, ItemFinder.find("trade"));

        String playerUUID = player.getUniqueId().toString();
        String friendUUID = friend.getUniqueId().toString();

        if (!CrystalRunes.isFriends(playerUUID, friendUUID)) {
            inventory.setItem(2, ItemFinder.find("friend-send"));
            return inventory;
        }

        inventory.setItem(2, ItemFinder.find("unfriend-send"));
        return inventory;
    }

    private static Inventory runeCreateMenu(Player player, String crystalUUID, int pRune, int sRune) {
        int amtSlots = 54;
        Inventory inventory = Bukkit.createInventory(null, amtSlots, ChatColor.LIGHT_PURPLE + "❖ Crystal Runes");
        Crystal crystal = CrystalFinder.findCrystal(player);
        // Create Runes on top of page
        inventory.setItem(0, ItemFinder.find("red-rune-primary"));
        inventory.setItem(1, ItemFinder.find("blue-rune-primary"));
        inventory.setItem(2, ItemFinder.find("green-rune-primary"));
        inventory.setItem(3, ItemFinder.find("yellow-rune-primary"));

        inventory.setItem(5, ItemFinder.find("red-rune-secondary"));
        inventory.setItem(6, ItemFinder.find("blue-rune-secondary"));
        inventory.setItem(7, ItemFinder.find("green-rune-secondary"));
        inventory.setItem(8, ItemFinder.find("yellow-rune-secondary"));

        List<ItemStack> pRunes = new ArrayList<>();
        List<ItemStack> sRunes = new ArrayList<>();

        switch (pRune) {
            case 0:
                for (int i = 9; i < 49; i++) {
                    // if on left side
                    if ((i % 9 == 0) || ((i - 1) % 9 == 0) || ((i - 2) % 9 == 0) || ((i - 3) % 9 == 0)) {
                        if (i % 2 == 0) {
                            inventory.setItem(i, ItemFinder.find("background-white"));
                        } else {
                            inventory.setItem(i, ItemFinder.find("background-gray"));
                        }
                    }
                }
                break;
            case 1:
                pRunes = getRunes("red", crystal);
                inventory.setItem(0, makeGlow(ItemFinder.find("red-rune-primary")));
                for (int i = 9; i < 49; i++) {
                    // if on left side
                    if ((i % 9 == 0) || ((i - 1) % 9 == 0) || ((i - 2) % 9 == 0) || ((i - 3) % 9 == 0)) {
                        if (i % 2 == 0) {
                            inventory.setItem(i, ItemFinder.find("background-red"));
                        } else {
                            inventory.setItem(i, ItemFinder.find("background-pink"));
                        }
                    }
                }
                break;
            case 2:
                pRunes = getRunes("blue", crystal);
                inventory.setItem(1, makeGlow(ItemFinder.find("blue-rune-primary")));
                for (int i = 9; i < 49; i++) {
                    // if on left side
                    if ((i % 9 == 0) || ((i - 1) % 9 == 0) || ((i - 2) % 9 == 0) || ((i - 3) % 9 == 0)) {
                        if (i % 2 == 0) {
                            inventory.setItem(i, ItemFinder.find("background-blue"));
                        } else {
                            inventory.setItem(i, ItemFinder.find("background-light-blue"));
                        }
                    }
                }
                break;
            case 3:
                pRunes = getRunes("green", crystal);
                inventory.setItem(2, makeGlow(ItemFinder.find("green-rune-primary")));
                for (int i = 9; i < 49; i++) {
                    // if on left side
                    if ((i % 9 == 0) || ((i - 1) % 9 == 0) || ((i - 2) % 9 == 0) || ((i - 3) % 9 == 0)) {
                        if (i % 2 == 0) {
                            inventory.setItem(i, ItemFinder.find("background-green"));
                        } else {
                            inventory.setItem(i, ItemFinder.find("background-lime"));
                        }
                    }
                }
                break;
            case 4:
                pRunes = getRunes("yellow", crystal);
                inventory.setItem(3, makeGlow(ItemFinder.find("yellow-rune-primary")));
                for (int i = 9; i < 49; i++) {
                    // if on left side
                    if ((i % 9 == 0) || ((i - 1) % 9 == 0) || ((i - 2) % 9 == 0) || ((i - 3) % 9 == 0)) {
                        if (i % 2 == 0) {
                            inventory.setItem(i, ItemFinder.find("background-yellow"));
                        } else {
                            inventory.setItem(i, ItemFinder.find("background-orange"));
                        }
                    }
                }
                break;
        }

        switch (sRune) {
            case 0:
                for (int i = 9; i < 54; i++) {
                    // if on right side
                    if (!((i % 9 == 0) || ((i - 1) % 9 == 0) || ((i - 2) % 9 == 0) || ((i - 3) % 9 == 0))) {
                        if (i % 2 == 0) {
                            inventory.setItem(i, ItemFinder.find("background-white"));
                        } else {
                            inventory.setItem(i, ItemFinder.find("background-gray"));
                        }
                    }

                }
                break;
            case 1:
                sRunes = getRunes("red", crystal);
                inventory.setItem(5, makeGlow(ItemFinder.find("red-rune-secondary")));
                for (int i = 9; i < 54; i++) {
                    // if on right side
                    if (!((i % 9 == 0) || ((i - 1) % 9 == 0) || ((i - 2) % 9 == 0) || ((i - 3) % 9 == 0))) {
                        if (i % 2 == 0) {
                            inventory.setItem(i, ItemFinder.find("background-red"));
                        } else {
                            inventory.setItem(i, ItemFinder.find("background-pink"));
                        }
                    }

                }
                break;
            case 2:
                sRunes = getRunes("blue", crystal);
                inventory.setItem(6, makeGlow(ItemFinder.find("blue-rune-secondary")));
                for (int i = 9; i < 54; i++) {
                    // if on right side
                    if (!((i % 9 == 0) || ((i - 1) % 9 == 0) || ((i - 2) % 9 == 0) || ((i - 3) % 9 == 0))) {
                        if (i % 2 == 0) {
                            inventory.setItem(i, ItemFinder.find("background-blue"));
                        } else {
                            inventory.setItem(i, ItemFinder.find("background-light-blue"));
                        }
                    }

                }
                break;
            case 3:
                sRunes = getRunes("green", crystal);
                inventory.setItem(7, makeGlow(ItemFinder.find("green-rune-secondary")));
                for (int i = 9; i < 54; i++) {
                    // if on right side
                    if (!((i % 9 == 0) || ((i - 1) % 9 == 0) || ((i - 2) % 9 == 0) || ((i - 3) % 9 == 0))) {
                        if (i % 2 == 0) {
                            inventory.setItem(i, ItemFinder.find("background-green"));
                        } else {
                            inventory.setItem(i, ItemFinder.find("background-lime"));
                        }
                    }

                }
                break;
            case 4:
                sRunes = getRunes("yellow", crystal);
                inventory.setItem(8, makeGlow(ItemFinder.find("yellow-rune-secondary")));
                for (int i = 9; i < 54; i++) {
                    // if on right side
                    if (!((i % 9 == 0) || ((i - 1) % 9 == 0) || ((i - 2) % 9 == 0) || ((i - 3) % 9 == 0))) {
                        if (i % 2 == 0) {
                            inventory.setItem(i, ItemFinder.find("background-yellow"));
                        } else {
                            inventory.setItem(i, ItemFinder.find("background-orange"));
                        }
                    }

                }
                break;
        }

        if (crystal.getTypePrimary() != 0 && crystal.getTypeSecondary() != 0) {
            // Add Primary Rune Items
            inventory.setItem(19, pRunes.get(0));
            inventory.setItem(20, pRunes.get(1));
            inventory.setItem(28, pRunes.get(2));
            inventory.setItem(29, pRunes.get(3));
            inventory.setItem(37, pRunes.get(4));
            inventory.setItem(38, pRunes.get(5));

            // Add Secondary Rune Items
            inventory.setItem(24, sRunes.get(0));
            inventory.setItem(25, sRunes.get(1));
            inventory.setItem(26, sRunes.get(2));
            inventory.setItem(33, sRunes.get(3));
            inventory.setItem(34, sRunes.get(4));
            inventory.setItem(35, sRunes.get(5));
        }

        // Create Gray Vertical Bar
        inventory.setItem(4, ItemFinder.find("background-black"));
        inventory.setItem(13, ItemFinder.find("background-black"));
        inventory.setItem(22, ItemFinder.find("background-black"));
        inventory.setItem(31, ItemFinder.find("background-black"));
        inventory.setItem(40, ItemFinder.find("background-black"));

        // Back and Save Button
        inventory.setItem(49, ItemFinder.find("left-arrow"));


        return inventory;
    }

    private static Inventory breakCrystalMenu() {
        int amtSlots = 27;

        Inventory inventory = Bukkit.createInventory(null, amtSlots, ChatColor.RED + "" + ChatColor.BOLD + "❖ Are You Sure?");

        for (int i = 0; i < amtSlots; i++) {
            if (i % 2 == 0) {
                inventory.setItem(i, ItemFinder.find("background-black"));
            } else {
                inventory.setItem(i, ItemFinder.find("background-red"));
            }
        }

        inventory.setItem(11, ItemFinder.find("decline-crystal-break"));
        inventory.setItem(15, ItemFinder.find("accept-crystal-break"));

        return inventory;
    }

    private static Inventory friendMenu(Player player, Boolean inCrystal) {
        int amtSlots = 45;
        if (inCrystal) {
            amtSlots = 54;
        }

        Inventory inventory = Bukkit.createInventory(null, amtSlots, ChatColor.LIGHT_PURPLE + "❖ Friends");

        List<String> friendUUIDs = CrystalRunes.getFriends(player.getUniqueId().toString());

        for (int i = 0; i < friendUUIDs.size(); i++) {
            ItemStack head = SkullCreator.itemFromUuid(UUID.fromString(friendUUIDs.get(i)));
            SkullMeta headMeta = (SkullMeta) head.getItemMeta();
            String uuid = headMeta.getOwningPlayer().getUniqueId().toString();
            String newName = headMeta.getOwningPlayer().getName();
            headMeta.setDisplayName(newName);

            PersistentDataContainer friendData = headMeta.getPersistentDataContainer();
            friendData.set(new NamespacedKey(CrystalRunes.getPlugin(), "uuid"), PersistentDataType.STRING, uuid);
            friendData.set(new NamespacedKey(CrystalRunes.getPlugin(), "name"), PersistentDataType.STRING, newName);

            head.setItemMeta(headMeta);
            inventory.setItem(i, head);
        }

        if (!inCrystal) {
            return inventory;
        }

        for (int i = 46; i < 54; i++) {
            inventory.setItem(i, ItemFinder.find("background-white"));
        }
        inventory.setItem(45, ItemFinder.find("left-arrow"));

        return inventory;

    }

    private static Inventory specificFriendMenu(String friendUUID, String friendName, Boolean inCrystal) {
        int amtSlots = 27;
        if (inCrystal) {
            amtSlots = 36;
        }

        Inventory inventory = Bukkit.createInventory(null, amtSlots, ChatColor.LIGHT_PURPLE + "❖ " + friendName);

        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, ItemFinder.find("background-gray"));
        }

        ItemStack head = SkullCreator.itemFromUuid(UUID.fromString(friendUUID));
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        String uuid = headMeta.getOwningPlayer().getUniqueId().toString();
        String newName = headMeta.getOwningPlayer().getName();
        headMeta.setDisplayName("Teleport to " + newName);
        head.setItemMeta(headMeta);

        inventory.setItem(15, head);

        ItemStack crystalItem = ItemFinder.find("tp-friend-crystal");
        ItemMeta crystalMeta = crystalItem.getItemMeta();
        PersistentDataContainer crystalData = crystalMeta.getPersistentDataContainer();
        crystalData.set(new NamespacedKey(CrystalRunes.getPlugin(), "uuid"), PersistentDataType.STRING, uuid);
        crystalData.set(new NamespacedKey(CrystalRunes.getPlugin(), "name"), PersistentDataType.STRING, newName);

        crystalItem.setItemMeta(crystalMeta);
        inventory.setItem(11, crystalItem);

        if (!inCrystal) {
            return inventory;
        }

        for (int i = 28; i < 36; i++) {
            inventory.setItem(i, ItemFinder.find("background-white"));
        }
        inventory.setItem(27, ItemFinder.find("back-friend-menu"));

        return inventory;
    }

    private static Inventory crystalSettings(Player player, String crystalUUID) {
        int amtSlots = 36;
        Inventory inventory = Bukkit.createInventory(null, amtSlots, "❖ Crystal Settings");

        Crystal crystal = CrystalRunes.getCrystal(player.getUniqueId().toString(), crystalUUID);

        int buildStatus = crystal.getBuildStatus();
        int tpStatus = crystal.getTpStatus();

        for (int i = 0; i < amtSlots; i++) {
            inventory.setItem(i, ItemFinder.find("background-gray"));
        }

        switch (buildStatus) {
            case 0 :
                inventory.setItem(11, ItemFinder.find("building-setting-green"));
                break;
            case 1:
                inventory.setItem(11, ItemFinder.find("building-setting-yellow"));
                break;
            case 2:
                inventory.setItem(11, ItemFinder.find("building-setting-red"));
                break;
        }

        switch (tpStatus) {
            case 0 :
                inventory.setItem(13, ItemFinder.find("teleport-setting-green"));
                break;
            case 1:
                inventory.setItem(13, ItemFinder.find("teleport-setting-yellow"));
                break;
            case 2:
                inventory.setItem(13, ItemFinder.find("teleport-setting-red"));
                break;
        }

        inventory.setItem(15, ItemFinder.find("change-name"));

        for (int i = 28; i < 36; i++) {
            inventory.setItem(i, ItemFinder.find("background-white"));
        }
        inventory.setItem(27, ItemFinder.find("left-arrow"));

        return inventory;
    }

    private static void sendInvite(Player sender, Player receiver) {

        sender.sendMessage(ChatColor.GOLD + "You have sent a friend request to " + ChatColor.WHITE + receiver.getDisplayName());
        sender.closeInventory();
        receiver.sendMessage(ChatColor.WHITE + sender.getDisplayName() + ChatColor.GOLD + " has sent you a friend request!");

        TextComponent message = new TextComponent("Click here to accept");
        message.setColor(net.md_5.bungee.api.ChatColor.GREEN);
        message.setUnderlined(true);
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend " + sender.getUniqueId()));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new ComponentBuilder("ʕっ•ᴥ•ʔっ").color(net.md_5.bungee.api.ChatColor.GOLD).create()));
        receiver.spigot().sendMessage(message);
    }

    private static void unFriend(Player sender, Player receiver) {

        String friendName = receiver.getName();
        String command = "/unfriend " + friendName;
        sender.chat(command);
        sender.closeInventory();
    }

    private static ItemStack makeGlow(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.LURE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static List<ItemStack> getRunes(String runeName, Crystal crystal) {

        List<ItemStack> runes = new ArrayList<>();

        int typeRune = 0;

        switch (runeName) {
            case "red":
                runes.add(ItemFinder.find("anti-tnt"));
                runes.add(ItemFinder.find("store-legendary"));
                runes.add(ItemFinder.find("anti-creeper"));
                runes.add(ItemFinder.find("untrackable"));
                runes.add(ItemFinder.find("anti-pvp"));
                runes.add(ItemFinder.find("no-fall"));
                typeRune = 10;
                break;
            case "blue":
                runes.add(ItemFinder.find("increase-xp"));
                runes.add(ItemFinder.find("increase-farm"));
                runes.add(ItemFinder.find("increase-ore"));
                runes.add(ItemFinder.find("increase-mob"));
                runes.add(ItemFinder.find("gold-farm"));
                runes.add(ItemFinder.find("gold-mob"));
                typeRune = 20;
                break;
            case "green":
                runes.add(ItemFinder.find("crop-tick"));
                runes.add(ItemFinder.find("spawner-tick"));
                runes.add(ItemFinder.find("monster-health"));
                runes.add(ItemFinder.find("animal-health"));
                runes.add(ItemFinder.find("smelt-time"));
                runes.add(ItemFinder.find("bottle-xp"));
                typeRune = 30;
                break;
            case "yellow":
                runes.add(ItemFinder.find("haste"));
                runes.add(ItemFinder.find("instant-gold"));
                runes.add(ItemFinder.find("double-jump"));
                runes.add(ItemFinder.find("big-chunk"));
                runes.add(ItemFinder.find("cobble-gen"));
                runes.add(ItemFinder.find("use-spawner"));
                typeRune = 40;
                break;
        }

        List<Integer> primaryRunes = new ArrayList<>();
        for (int i = 0; i < crystal.getPrimaryRunes().size(); i++) {
            primaryRunes.add(crystal.getPrimaryRunes().get(i));
        }

        for (int i = 0; i < primaryRunes.size(); i++) {
            int indexRune = primaryRunes.get(i) - typeRune;
            primaryRunes.set(i, indexRune);
        }

        for (int i = 0; i < runes.size(); i++) {
            if (primaryRunes.contains(i)) {
                runes.set(i, makeGlow(runes.get(i)));
            }
        }

        List<Integer> secondaryRunes = new ArrayList<>();
        for (int i = 0; i < crystal.getSecondaryRunes().size(); i++) {
            secondaryRunes.add(crystal.getSecondaryRunes().get(i));
        }

        for (int i = 0; i < secondaryRunes.size(); i++) {
            int indexRune = secondaryRunes.get(i) - typeRune;
            secondaryRunes.set(i, indexRune);
        }

        for (int i = 0; i < runes.size(); i++) {
            if (secondaryRunes.contains(i)) {
                runes.set(i, makeGlow(runes.get(i)));
            }
        }




        return runes;
    }
}
