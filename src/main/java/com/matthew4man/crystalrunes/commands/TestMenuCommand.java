package com.matthew4man.crystalrunes.commands;

import com.matthew4man.crystalrunes.menus.CrystalMainMenu;
import com.matthew4man.crystalrunes.menus.ItemTesterMenu;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.MenuManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TestMenuCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        try {

            switch (args[0]) {
                case "crystalmenu":
                    MenuManager.openMenu(CrystalMainMenu.class, player);
                    break;
                case "itemtester":
                    MenuManager.openMenu(ItemTesterMenu.class, player);
                    break;
            }

        } catch (MenuManagerException e) {
            e.printStackTrace();
        } catch (MenuManagerNotSetupException e) {
            e.printStackTrace();
        }

        return true;
    }
}
