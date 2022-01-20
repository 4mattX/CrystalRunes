package com.matthew4man.crystalrunes.menus;

import com.matthew4man.crystalrunes.objects.Crystal;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class CrystalMainMenu extends Menu {

    String menuName = "UNNAMED";

    public CrystalMainMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    public CrystalMainMenu(PlayerMenuUtility playerMenuUtility, Crystal crystal) {
        super(playerMenuUtility);
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }


    @Override
    public String getMenuName() {
        return menuName;
    }

    @Override
    public int getSlots() {
        return 45;
    }

    @Override
    public boolean cancelAllClicks() {
        return true;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) throws MenuManagerNotSetupException, MenuManagerException {

    }

    @Override
    public void setMenuItems() {

        ItemStack background1 = makeItem(Material.GRAY_STAINED_GLASS_PANE, ".");
        ItemStack background2 = makeItem(Material.BLACK_STAINED_GLASS_PANE, ".");

        ItemStack playerCrystals = makeItem(Material.GRASS_BLOCK, ChatColor.GREEN + "Your Crystals");
        ItemStack friendCrystals = makeItem(Material.WARPED_NYLIUM, ChatColor.AQUA + "Friend Crystals");
        ItemStack publicCrystals = makeItem(Material.GRASS_BLOCK, ChatColor.RED + "Public Crystals");
        ItemStack runes = makeItem(Material.AMETHYST_CLUSTER, ChatColor.LIGHT_PURPLE + "RUNES");
        ItemStack settings = makeItem(Material.COMPARATOR, ChatColor.WHITE + "Crystal Settings");
        ItemStack breakButton = makeItem(Material.BARRIER, ChatColor.DARK_RED + "Break Crystal");
        ItemStack gold = makeItem(Material.RAW_GOLD, ChatColor.GOLD + "Gold");

        // Sets the background of menu
        for (int i = 0; i < getSlots(); i++) {
            if (i % 2 == 0) {
                inventory.setItem(i, background1);
                continue;
            }
            inventory.setItem(i, background2);
        }

        inventory.setItem(4, playerCrystals);
        inventory.setItem(11, friendCrystals);
        inventory.setItem(15, publicCrystals);
        inventory.setItem(22, runes);
        inventory.setItem(29, settings);
        inventory.setItem(33, breakButton);
        inventory.setItem(40, gold);
    }
}
