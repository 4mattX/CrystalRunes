package com.matthew4man.crystalrunes.listeners.crystalListeners;

import com.matthew4man.crystalrunes.CrystalRunes;
import com.matthew4man.crystalrunes.menus.CrystalMainMenu;
import com.matthew4man.crystalrunes.menus.MenuMachine;
import com.matthew4man.crystalrunes.objects.Crystal;
import me.kodysimpson.simpapi.exceptions.MenuManagerException;
import me.kodysimpson.simpapi.exceptions.MenuManagerNotSetupException;
import me.kodysimpson.simpapi.menu.Menu;
import me.kodysimpson.simpapi.menu.MenuManager;
import me.kodysimpson.simpapi.menu.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EnderCrystal;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class CrystalInteract implements Listener {

    @EventHandler
    public void onCrystalRightClick(PlayerInteractEntityEvent event) throws MenuManagerNotSetupException, MenuManagerException {

        if (!(event.getRightClicked() instanceof EnderCrystal)) {
            return;
        }

        if (event.getPlayer().isSneaking()) {
            return;
        }

        Entity crystalEntity = event.getRightClicked();
        Player player = event.getPlayer();

        PersistentDataContainer crystalData = crystalEntity.getPersistentDataContainer();
        String crystalUUID = crystalData.get(new NamespacedKey(CrystalRunes.getPlugin(), "crystalUUID"), PersistentDataType.STRING);
        String playerUUID = crystalData.get(new NamespacedKey(CrystalRunes.getPlugin(), "playerUUID"), PersistentDataType.STRING);

        Crystal crystal = CrystalRunes.getCrystal(playerUUID, crystalUUID);

        if (crystal.getTypePrimary() == 5) {
            MenuMachine.openMenu(player, "server-crystal-menu", crystalUUID);
            return;
        }

        MenuMachine.openMenu(player, "crystal-main-menu", crystalUUID);
    }
}
