package com.matthew4man.crystalrunes.listeners.playerListeners;

import com.matthew4man.crystalrunes.CrystalRunes;
import com.matthew4man.crystalrunes.menus.MenuMachine;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerInventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        Inventory inventory = event.getInventory();

        if (inventory.getType() != InventoryType.CRAFTING) {
            return;
        }

        if (event.getSlot() < 5) {

            try {
                if (event.getCurrentItem().getItemMeta().getLore().get(0).contains("Click")) {
                    event.setCancelled(true);
                    // CRAFTING TABLE
                    if (event.getSlot() == 1) {
                        player.closeInventory();

                        Bukkit.getScheduler().runTaskLater(CrystalRunes.getPlugin(), () -> {
                            player.openWorkbench(null, true);
                        }, 1L);

                    }

                    // FRIENDS
                    if (event.getSlot() == 2) {
                        player.closeInventory();

                        Bukkit.getScheduler().runTaskLater(CrystalRunes.getPlugin(), () -> {
                            MenuMachine.openMenu(player, "friend-menu", "");
                        }, 1L);

                    }


                    // Personal CRYSTALS
                    if (event.getSlot() == 3) {
                        player.closeInventory();

                        Bukkit.getScheduler().runTaskLater(CrystalRunes.getPlugin(), () -> {
                            MenuMachine.openMenu(player, "player-crystals-menu", "");
                        }, 1L);

                    }

                    // Public CRYSTALS
                    if (event.getSlot() == 4) {
                        player.closeInventory();

                        Bukkit.getScheduler().runTaskLater(CrystalRunes.getPlugin(), () -> {
                            MenuMachine.openMenu(player, "public-crystals-menu", "");
                        }, 1L);

                    }

                }
            } catch (Exception e) {
            }
        }

        CrystalRunes.addInventory(player.getUniqueId().toString(), inventory);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {

        try {
            Player player = (Player) event.getPlayer();

            Inventory inventory = CrystalRunes.getPlayerInventory(player.getUniqueId().toString());

            ItemStack air = new ItemStack(Material.AIR);

            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, air);
            }
        } catch (Exception e) {
        }

    }

}
