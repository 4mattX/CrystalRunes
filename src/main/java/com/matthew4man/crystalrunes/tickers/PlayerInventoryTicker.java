package com.matthew4man.crystalrunes.tickers;

import com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
import com.matthew4man.crystalrunes.CrystalRunes;
import com.matthew4man.crystalrunes.objects.Crystal;
import com.matthew4man.crystalrunes.objects.CrystalChunk;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class PlayerInventoryTicker {

    private ItemStack[] slots = new ItemStack[4];


    public void start() {
        setSlots();

        new BukkitRunnable() {

            @Override
            public void run() {

                // Player Inventory
                for (Inventory inventory : CrystalRunes.getInventoryHashMap().values()) {
                    inventory.setItem(1, slots[0]);
                    inventory.setItem(2, slots[1]);
                    inventory.setItem(3, slots[2]);
                    inventory.setItem(4, slots[3]);
                }

                Bukkit.getOnlinePlayers().forEach(player -> {
                    for (int i = 0; i < 36; i++) {

                        try {
                            if (player.getInventory().getItem(i).getItemMeta().getLore().get(0).contains("Click")) {
                                player.getInventory().setItem(i, new ItemStack(Material.AIR));
                            }
                        } catch (Exception e) {
                        }
                    }

                    PersistentDataContainer playerData = player.getPersistentDataContainer();
                    int borderBoolean = playerData.get(new NamespacedKey(CrystalRunes.getPlugin(), "borderBoolean"), PersistentDataType.INTEGER);

                    if (borderBoolean == 1) {
                        return;
                    }

                    if (player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getItemMeta() == null || player.getInventory().getItemInMainHand().getItemMeta().getLore() == null || player.getInventory().getItemInMainHand().getItemMeta().getLore().get(0) == null) {
                        WorldBorderApi worldBorderApi = CrystalRunes.getWorldBorderApi();
                        worldBorderApi.resetWorldBorderToGlobal(player);
                        return;
                    }

                    if (player.getInventory().getItemInHand().getItemMeta().getLore().get(0).contains("Crystal")) {

                        int xSign = 1;
                        int zSign = 1;

                        if (player.getLocation().getX() < 0) {
                            xSign *= -1;
                        }

                        if (player.getLocation().getZ() < 0) {
                            zSign *= -1;
                        }

                        int minX = Math.abs((int) (player.getLocation().getX() / 16));
                        int minZ = Math.abs((int) (player.getLocation().getZ() / 16));
                        double xLoc = ((minX * 16) + 8) * xSign;
                        double zLoc = ((minZ * 16) + 8) * zSign;

                        WorldBorderApi worldBorderApi = CrystalRunes.getWorldBorderApi();

                        Location location = new Location(player.getWorld(), xLoc, 0, zLoc);

                        worldBorderApi.setBorder(player, 48, location);

                    } else {
                        WorldBorderApi worldBorderApi = CrystalRunes.getWorldBorderApi();
                        worldBorderApi.resetWorldBorderToGlobal(player);
                    }


                });

            }
        }.runTaskTimer(CrystalRunes.getPlugin(), 0, 10);
    }

    private void setSlots() {
        ItemStack crafting = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta craftingMeta = crafting.getItemMeta();
        craftingMeta.setDisplayName(ChatColor.GOLD + "Crafting");
        ArrayList<String> craftingLore = new ArrayList<>();
        craftingLore.add(ChatColor.GRAY + "Click to open 3x3");
        craftingLore.add(ChatColor.GRAY + "Crafting Table");
        craftingMeta.setLore(craftingLore);
        crafting.setItemMeta(craftingMeta);

        ItemStack friend = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta friendMeta = friend.getItemMeta();
        friendMeta.setDisplayName(ChatColor.GOLD + "Friends");
        ArrayList<String> friendLore = new ArrayList<>();
        friendLore.add(ChatColor.GRAY + "Click to view friends");
        friendMeta.setLore(friendLore);
        friend.setItemMeta(friendMeta);

        ItemStack crystals = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta crystalsMeta = crystals.getItemMeta();
        crystalsMeta.setDisplayName(ChatColor.GOLD + "Personal Crystals");
        ArrayList<String> crystalsLore = new ArrayList<>();
        crystalsLore.add(ChatColor.GRAY + "Click to access Personal Crystals");
        crystalsMeta.setLore(crystalsLore);
        crystals.setItemMeta(crystalsMeta);

        ItemStack runes = new ItemStack(Material.CRIMSON_NYLIUM);
        ItemMeta runesMeta = runes.getItemMeta();
        runesMeta.setDisplayName(ChatColor.GOLD + "Public Crystals");
        ArrayList<String> runesLore = new ArrayList<>();
        runesLore.add(ChatColor.GRAY + "Click to access Public Crystals");
        runesMeta.setLore(runesLore);
        runes.setItemMeta(runesMeta);

        slots[0] = crafting;
        slots[1] = friend;
        slots[2] = crystals;
        slots[3] = runes;
    }

}
