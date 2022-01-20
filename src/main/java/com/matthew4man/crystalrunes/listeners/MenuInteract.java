package com.matthew4man.crystalrunes.listeners;

import com.matthew4man.crystalrunes.CrystalRunes;
import com.matthew4man.crystalrunes.menus.MenuMachine;
import com.matthew4man.crystalrunes.methods.CrystalDeleter;
import com.matthew4man.crystalrunes.methods.CrystalFinder;
import com.matthew4man.crystalrunes.methods.ItemFinder;
import com.matthew4man.crystalrunes.objects.Crystal;
import com.matthew4man.crystalrunes.objects.Teleporter;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Skull;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class MenuInteract implements Listener {

    @EventHandler
    public void onButtonClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (!(event.getView().getTitle().contains("❖"))) {
            return;
        }
        event.setCancelled(true);

        if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null || event.getCurrentItem().getItemMeta().getDisplayName() == null) {
            return;
        }

        // All different button logic
        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Your Crystals")) {
            try {
                MenuMachine.buttonUse(player, "player-crystals");
            } catch (Exception e) {
            }
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Public Crystals")) {
            Crystal crystal = CrystalFinder.getCrystalFromChunk(player.getLocation());
            MenuMachine.openMenu(player, "public-crystals-menu", crystal.getUuid());
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.DARK_GRAY + "Back")) {
            player.closeInventory();
            Crystal crystal = CrystalFinder.getCrystalFromChunk(player.getLocation());

            if (!crystal.getOwnerUUID().equals(player.getUniqueId().toString())) {
                player.closeInventory();
                return;
            }

            Bukkit.getScheduler().runTaskLater(CrystalRunes.getPlugin(), () -> {

                if (crystal.getTypePrimary() == 5) {
                    MenuMachine.openMenu(player, "server-crystal-menu", crystal.getUuid());
                } else {
                    MenuMachine.buttonUse(player, "crystal-menu");
                }

            }, 1L);
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Send Friend Request")) {
            MenuMachine.buttonUse(player, "send-friend-request");
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Remove Friend")) {
            MenuMachine.buttonUse(player, "remove-friend");
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Change Name")) {

            Crystal crystal = CrystalFinder.findCrystal(player);

            new AnvilGUI.Builder()
                    .onComplete((user, text) -> {
                        crystal.setName(text);
                        user.sendMessage(ChatColor.GREEN + "Crystal name changed to: " + ChatColor.GOLD + text);
                        return AnvilGUI.Response.close();
                    })
                    .text(crystal.getName())
                    .title("Change Name")
                    .plugin(CrystalRunes.getPlugin())
                    .open(player);
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("RUNES")) {
            MenuMachine.buttonUse(player, "crystal-runes");
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Break Crystal")) {
            MenuMachine.buttonUse(player, "crystal-break");
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Do Not Break")) {
            MenuMachine.buttonUse(player, "crystal-menu");
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Crystal Settings")) {
            Crystal crystal = CrystalFinder.findCrystal(player);
            String crystalUUID = crystal.getUuid();
            MenuMachine.openMenu(player, "crystal-settings-menu", crystalUUID);
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Back to Friends")) {
            Crystal crystal = CrystalFinder.getCrystalFromChunk(player.getLocation());

            String crystalUUID = "";

            if (crystal != null) {
                crystalUUID = crystal.getUuid();
            }

            try {
                MenuMachine.openMenu(player, "friend-menu", crystalUUID);
            } catch (Exception e) {
                player.closeInventory();
            }
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Friend Crystals")) {
            Crystal crystal = CrystalFinder.getCrystalFromChunk(player.getLocation());
            MenuMachine.openMenu(player, "friend-menu", crystal.getUuid());
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Teleport to")) {

            String playerName = event.getCurrentItem().getItemMeta().getDisplayName().split("to ")[1];
            player.performCommand("tpa " + playerName);
            return;
        }

        if (event.getCurrentItem().getType().equals(Material.PLAYER_HEAD) && !event.getCurrentItem().getItemMeta().getDisplayName().contains("Back")) {

            ItemMeta skullMeta = event.getCurrentItem().getItemMeta();
            PersistentDataContainer skullData = skullMeta.getPersistentDataContainer();
            String friendUUID = skullData.get(new NamespacedKey(CrystalRunes.getPlugin(), "uuid"), PersistentDataType.STRING);
            String friendName = skullData.get(new NamespacedKey(CrystalRunes.getPlugin(), "name"), PersistentDataType.STRING);

            Crystal crystal = CrystalFinder.getCrystalFromChunk(player.getLocation());

            String crystalUUID = "";
            if (crystal != null) {
                crystalUUID = crystal.getUuid();
            }

            MenuMachine.openMenu(player, friendUUID, friendName, "specific-friend-menu", crystalUUID);
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Yes, Break")) {
            Entity crystal = null;

            for (Entity entity : player.getNearbyEntities(6, 6, 6)) {
                if (entity.getType() == EntityType.ENDER_CRYSTAL) {
                    crystal = entity;
                }
            }

            String crystalUUID = "";

            player.closeInventory();

            try {
                PersistentDataContainer crystalData = crystal.getPersistentDataContainer();
                crystalUUID = crystalData.get(new NamespacedKey(CrystalRunes.getPlugin(), "crystalUUID"), PersistentDataType.STRING);
            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "You are too far away from the crystal");
                player.sendMessage(ChatColor.RED + "Try again");
                return;
            }

            Crystal playerCrystal = CrystalRunes.getCrystal(player.getUniqueId().toString(), crystalUUID);

            ItemStack crystalItem = null;

            if (playerCrystal.getTypePrimary() == 1) {

                if (playerCrystal.getTypeSecondary() == 2) {
                    crystalItem = ItemFinder.find("red-blue-crystal");
                }
                if (playerCrystal.getTypeSecondary() == 3) {
                    crystalItem = ItemFinder.find("red-green-crystal");
                }
                if (playerCrystal.getTypeSecondary() == 4) {
                    crystalItem = ItemFinder.find("red-yellow-crystal");
                }
            }

            else if (playerCrystal.getTypePrimary() == 2) {

                if (playerCrystal.getTypeSecondary() == 1) {
                    crystalItem = ItemFinder.find("red-blue-crystal");
                }
                if (playerCrystal.getTypeSecondary() == 3) {
                    crystalItem = ItemFinder.find("blue-green-crystal");
                }
                if (playerCrystal.getTypeSecondary() == 4) {
                    crystalItem = ItemFinder.find("blue-yellow-crystal");
                }
            }

            else if (playerCrystal.getTypePrimary() == 3) {

                if (playerCrystal.getTypeSecondary() == 1) {
                    crystalItem = ItemFinder.find("red-green-crystal");
                }
                if (playerCrystal.getTypeSecondary() == 2) {
                    crystalItem = ItemFinder.find("blue-green-crystal");
                }
                if (playerCrystal.getTypeSecondary() == 4) {
                    crystalItem = ItemFinder.find("green-yellow-crystal");
                }
            }

            else if (playerCrystal.getTypePrimary() == 4) {

                if (playerCrystal.getTypeSecondary() == 1) {
                    crystalItem = ItemFinder.find("red-yellow-crystal");
                }
                if (playerCrystal.getTypeSecondary() == 2) {
                    crystalItem = ItemFinder.find("blue-yellow-crystal");
                }
                if (playerCrystal.getTypeSecondary() == 3) {
                    crystalItem = ItemFinder.find("green-yellow-crystal");
                }
            }

            else {
                crystalItem = ItemFinder.find("default-crystal");
            }

            CrystalDeleter.delete(crystal);
            playerCrystal.getWorld().dropItemNaturally(playerCrystal.getLocation(), crystalItem);

        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Friend's Crystals")) {
            ItemMeta itemMeta = event.getCurrentItem().getItemMeta();
            PersistentDataContainer itemData = itemMeta.getPersistentDataContainer();
            String friendUUID = itemData.get(new NamespacedKey(CrystalRunes.getPlugin(), "uuid"), PersistentDataType.STRING);
            String friendName = itemData.get(new NamespacedKey(CrystalRunes.getPlugin(), "name"), PersistentDataType.STRING);

            System.out.println(event.getCurrentItem().getType().toString());
            System.out.println("HERE " + friendUUID);
            System.out.println("HERE " + friendName);

            Crystal crystal = CrystalFinder.getCrystalFromChunk(player.getLocation());

            String crystalUUID = "";
            if (crystal != null) {
                crystalUUID = crystal.getUuid();
            }

            MenuMachine.openMenu(player, friendUUID, friendName, "friend-crystal-menu", crystalUUID);

        }

        // LORE SPECIFIC UNDER HERE

        if (event.getCurrentItem().getItemMeta().getLore() == null) {
            return;
        }

        if (event.getCurrentItem().getItemMeta().getLore().get(0).contains("/")) {
            Crystal crystal = CrystalFinder.findCrystal(player);
            String crystalUUID = crystal.getUuid();

            // Changing Crystal Settings
            // Building
            if (event.getCurrentItem().getType().equals(Material.LIME_SHULKER_BOX) && event.getCurrentItem().getItemMeta().getDisplayName().contains("Building")) {
                crystal.setBuildStatus(1);
                MenuMachine.openMenu(player, "crystal-settings-menu", crystalUUID);
            }

            if (event.getCurrentItem().getType().equals(Material.YELLOW_SHULKER_BOX) && event.getCurrentItem().getItemMeta().getDisplayName().contains("Building")) {
                crystal.setBuildStatus(2);
                MenuMachine.openMenu(player, "crystal-settings-menu", crystalUUID);
            }

            if (event.getCurrentItem().getType().equals(Material.RED_SHULKER_BOX) && event.getCurrentItem().getItemMeta().getDisplayName().contains("Building")) {
                crystal.setBuildStatus(0);
                MenuMachine.openMenu(player, "crystal-settings-menu", crystalUUID);
            }

            // Teleporting
            if (event.getCurrentItem().getType().equals(Material.LIME_SHULKER_BOX) && event.getCurrentItem().getItemMeta().getDisplayName().contains("Teleport")) {
                crystal.setTpStatus(1);
                MenuMachine.openMenu(player, "crystal-settings-menu", crystalUUID);
            }

            if (event.getCurrentItem().getType().equals(Material.YELLOW_SHULKER_BOX) && event.getCurrentItem().getItemMeta().getDisplayName().contains("Teleport")) {
                crystal.setTpStatus(2);
                MenuMachine.openMenu(player, "crystal-settings-menu", crystalUUID);
            }

            if (event.getCurrentItem().getType().equals(Material.RED_SHULKER_BOX) && event.getCurrentItem().getItemMeta().getDisplayName().contains("Teleport")) {
                crystal.setTpStatus(0);
                MenuMachine.openMenu(player, "crystal-settings-menu", crystalUUID);
            }
        }

        if (event.getCurrentItem().getItemMeta().getLore().get(0).contains("❖")) {

            // When teleporting to players
            if (!event.getCurrentItem().getItemMeta().getLore().get(0).contains("/")) {
                PersistentDataContainer crystalItemData = event.getCurrentItem().getItemMeta().getPersistentDataContainer();
                String crystalUUID = crystalItemData.get(new NamespacedKey(CrystalRunes.getPlugin(), "crystalUUID"), PersistentDataType.STRING);

                MenuMachine.buttonTeleport(player, crystalUUID);
            }

        }
    }

    @EventHandler
    public void onRuneButtonClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (!(event.getView().getTitle().contains("❖ Crystal Runes"))) {
            return;
        }
        event.setCancelled(true);

        if (event.getCurrentItem() == null || event.getCurrentItem().getItemMeta() == null || event.getCurrentItem().getItemMeta().getDisplayName() == null) {
            return;
        }

        Crystal crystal = CrystalFinder.findCrystal(player);

        // Rune Buttons
        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Red Primary")) {

            if (crystal.getTypeSecondary() == 1) {
                return;
            }
            crystal.clearPrimaryRunes();

            crystal.setTypePrimary(1);
            MenuMachine.buttonUse(player, "crystal-runes");
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Blue Primary")) {

            if (crystal.getTypeSecondary() == 2) {
                return;
            }
            crystal.clearPrimaryRunes();

            crystal.setTypePrimary(2);
            MenuMachine.buttonUse(player, "crystal-runes");
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Green Primary")) {

            if (crystal.getTypeSecondary() == 3) {
                return;
            }
            crystal.clearPrimaryRunes();

            crystal.setTypePrimary(3);
            MenuMachine.buttonUse(player, "crystal-runes");
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Yellow Primary")) {

            if (crystal.getTypeSecondary() == 4) {
                return;
            }
            crystal.clearPrimaryRunes();

            crystal.setTypePrimary(4);
            MenuMachine.buttonUse(player, "crystal-runes");
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Red Secondary")) {

            if (crystal.getTypePrimary() == 1) {
                return;
            }
            crystal.clearSecondaryRunes();

            crystal.setTypeSecondary(1);
            MenuMachine.buttonUse(player, "crystal-runes");
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Blue Secondary")) {

            if (crystal.getTypePrimary() == 2) {
                return;
            }
            crystal.clearSecondaryRunes();

            crystal.setTypeSecondary(2);
            MenuMachine.buttonUse(player, "crystal-runes");
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Green Secondary")) {

            if (crystal.getTypePrimary() == 3) {
                return;
            }
            crystal.clearSecondaryRunes();

            crystal.setTypeSecondary(3);
            MenuMachine.buttonUse(player, "crystal-runes");
        }

        if (event.getCurrentItem().getItemMeta().getDisplayName().contains("Yellow Secondary")) {

            if (crystal.getTypePrimary() == 4) {
                return;
            }
            crystal.clearSecondaryRunes();

            crystal.setTypeSecondary(4);
            MenuMachine.buttonUse(player, "crystal-runes");
        }

        // Specific Primary Runes
        int pRuneID = -1;
        int pRuneType = crystal.getTypePrimary();


        pRuneID = primaryRuneSelect(event.getSlot(), pRuneType);

        List<Integer> primaryRuneList = crystal.getPrimaryRunes();

        // Prevent selecting runes on same row
        for (int i = 0; i < primaryRuneList.size(); i++) {

            int runeSlotIndex = getSlotFromRuneID(primaryRuneList.get(i), true);
            int selectedSlotIndex = event.getSlot();

            // If selected on same row, then swap old with new
            if ((selectedSlotIndex + 1) == runeSlotIndex || (selectedSlotIndex - 1) == runeSlotIndex) {

                List<Integer> runeList = new ArrayList<>();
                for (int j = 0; j < crystal.getPrimaryRunes().size(); j++) {
                    runeList.add(crystal.getPrimaryRunes().get(j));
                }

                // If in first row
                if (selectedSlotIndex < 21) {
                    if (pRuneID < 20) {
                        runeList.remove((Object) 10);
                        runeList.remove((Object) 11);
                    } else if (pRuneID < 30) {
                        runeList.remove((Object) 20);
                        runeList.remove((Object) 21);
                    } else if (pRuneID < 40) {
                        runeList.remove((Object) 30);
                        runeList.remove((Object) 31);
                    } else {
                        runeList.remove((Object) 40);
                        runeList.remove((Object) 41);
                    }
                }
                // If in second row
                else if (selectedSlotIndex > 27 && selectedSlotIndex < 30) {
                    if (pRuneID < 20) {
                        runeList.remove((Object) 12);
                        runeList.remove((Object) 13);
                    } else if (pRuneID < 30) {
                        runeList.remove((Object) 22);
                        runeList.remove((Object) 23);
                    } else if (pRuneID < 40) {
                        runeList.remove((Object) 32);
                        runeList.remove((Object) 33);
                    } else {
                        runeList.remove((Object) 42);
                        runeList.remove((Object) 43);
                    }
                }
                // If in third row
                else {
                    if (pRuneID < 20) {
                        runeList.remove((Object) 14);
                        runeList.remove((Object) 15);
                    } else if (pRuneID < 30) {
                        runeList.remove((Object) 24);
                        runeList.remove((Object) 25);
                    } else if (pRuneID < 40) {
                        runeList.remove((Object) 34);
                        runeList.remove((Object) 35);
                    } else {
                        runeList.remove((Object) 44);
                        runeList.remove((Object) 45);
                    }
                }

                runeList.add(pRuneID);

                crystal.clearPrimaryRunes();

                runeList.forEach(rune -> {
                    crystal.addPrimaryRune(rune);
                });

                MenuMachine.buttonUse(player, "crystal-runes");

                return;
            }

        }

        if (pRuneID != 0) {

            if (!crystal.getPrimaryRunes().contains(pRuneID)) {
                crystal.addPrimaryRune(pRuneID);
            }
        }

        // Specific Secondary Runes
        int sRuneID = 0;
        int sRuneType = crystal.getTypeSecondary();


        sRuneID = secondaryRuneSelect(event.getSlot(), sRuneType);

        List<Integer> secondaryRuneList = crystal.getSecondaryRunes();

        for (int i = 0; i < secondaryRuneList.size(); i++) {

            int runeSlotIndex = getSlotFromRuneID(secondaryRuneList.get(i), false);
            int selectedSlotIndex = event.getSlot();

            if ((selectedSlotIndex + 1) == runeSlotIndex || (selectedSlotIndex - 1) == runeSlotIndex || (selectedSlotIndex + 2) == runeSlotIndex || (selectedSlotIndex - 2) == runeSlotIndex) {

                List<Integer> runeList = new ArrayList<>();
                for (int j = 0; j < crystal.getSecondaryRunes().size(); j++) {
                    runeList.add(crystal.getSecondaryRunes().get(j));
                }

                // If in first row
                if (selectedSlotIndex > 23 && selectedSlotIndex < 27) {
                    if (sRuneID < 20) {
                        runeList.remove((Object) 10);
                        runeList.remove((Object) 11);
                        runeList.remove((Object) 12);
                    } else if (sRuneID < 30) {
                        runeList.remove((Object) 20);
                        runeList.remove((Object) 21);
                        runeList.remove((Object) 22);
                    } else if (sRuneID < 40) {
                        runeList.remove((Object) 30);
                        runeList.remove((Object) 31);
                        runeList.remove((Object) 32);
                    } else {
                        runeList.remove((Object) 40);
                        runeList.remove((Object) 41);
                        runeList.remove((Object) 42);
                    }
                }
                // If in second row
                if (selectedSlotIndex > 32 && selectedSlotIndex < 36) {
                    if (sRuneID < 20) {
                        runeList.remove((Object) 13);
                        runeList.remove((Object) 14);
                        runeList.remove((Object) 15);
                    } else if (sRuneID < 30) {
                        runeList.remove((Object) 23);
                        runeList.remove((Object) 24);
                        runeList.remove((Object) 25);
                    } else if (sRuneID < 40) {
                        runeList.remove((Object) 33);
                        runeList.remove((Object) 34);
                        runeList.remove((Object) 35);
                    } else {
                        runeList.remove((Object) 43);
                        runeList.remove((Object) 44);
                        runeList.remove((Object) 45);
                    }
                }

                runeList.add(sRuneID);

                crystal.clearSecondaryRunes();

                runeList.forEach(rune -> {
                    crystal.addSecondaryRune(rune);
                });

                MenuMachine.buttonUse(player, "crystal-runes");
                return;
            }
        }

        if (sRuneID != 0) {

            if (!crystal.getSecondaryRunes().contains(sRuneID)) {
                crystal.addSecondaryRune(sRuneID);
            }
        }

        MenuMachine.buttonUse(player, "crystal-runes");
    }

    private static int primaryRuneSelect(int itemSlot, int runeType) {

        int runeID = 0;

        if (itemSlot == 19) {
            runeID += 0;
        }

        else if (itemSlot == 20) {
            runeID += 1;
        }

        else if (itemSlot == 28) {
            runeID += 2;
        }

        else if (itemSlot == 29) {
            runeID += 3;
        }

        else if (itemSlot == 37) {
            runeID += 4;
        }

        else if (itemSlot == 38) {
            runeID += 5;
        }

        else {
            return 0;
        }

        switch (runeType) {
            case 1:
                runeID += 10;
                break;
            case 2:
                runeID += 20;
                break;
            case 3:
                runeID += 30;
                break;
            case 4:
                runeID += 40;
                break;
        }

        return runeID;
    }

    private static int secondaryRuneSelect(int itemSlot, int runeType) {

        int runeID = 0;

        if (itemSlot == 24) {
            runeID += 0;
        }

        else if (itemSlot == 25) {
            runeID += 1;
        }

        else if (itemSlot == 26) {
            runeID += 2;
        }

        else if (itemSlot == 33) {
            runeID += 3;
        }

        else if (itemSlot == 34) {
            runeID += 4;
        }

        else if (itemSlot == 35) {
            runeID += 5;
        }

        else {
            return 0;
        }

        switch (runeType) {
            case 1:
                runeID += 10;
                break;
            case 2:
                runeID += 20;
                break;
            case 3:
                runeID += 30;
                break;
            case 4:
                runeID += 40;
                break;
        }

        return runeID;
    }

    private static int getSlotFromRuneID(int runeID, boolean primary) {

        int index = 0;

        // Red Runes
        if (runeID < 20) {
            index = runeID - 10;
        }
        // Blue Runes
        else if (runeID < 30) {
            index = runeID - 20;
        }
        // Green Runes
        else if (runeID < 40) {
            index = runeID - 30;
        }
        // Yellow Runes
        else if (runeID < 50) {
            index = runeID - 40;
        }

        if (primary) {
            if (index == 0) {
                return 19;
            }

            if (index == 1) {
                return 20;
            }

            if (index == 2) {
                return 28;
            }

            if (index == 3) {
                return 29;
            }

            if (index == 4) {
                return 37;
            }

            if (index == 5) {
                return 38;
            }
        }

        if (!primary) {
            if (index == 0) {
                return 24;
            }

            if (index == 1) {
                return 25;
            }

            if (index == 2) {
                return 26;
            }

            if (index == 3) {
                return 33;
            }

            if (index == 4) {
                return 34;
            }

            if (index == 5) {
                return 35;
            }
        }

        return -1;
    }

}
