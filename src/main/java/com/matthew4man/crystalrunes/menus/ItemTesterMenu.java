package com.matthew4man.crystalrunes.menus;

import com.matthew4man.crystalrunes.methods.ItemFinder;
import com.matthew4man.crystalrunes.objects.PlayerSoul;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ItemTesterMenu extends Menu {

    public ItemTesterMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Items";
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public boolean cancelAllClicks() {
        return false;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) throws MenuManagerNotSetupException, MenuManagerException {

    }

    @Override
    public void setMenuItems() {
        inventory.setItem(0, ItemFinder.find("red-blue-crystal"));
        inventory.setItem(1, PlayerSoul.getSoul());
    }
}
