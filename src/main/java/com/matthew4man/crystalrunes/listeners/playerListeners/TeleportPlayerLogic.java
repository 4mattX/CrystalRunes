package com.matthew4man.crystalrunes.listeners.playerListeners;

import com.matthew4man.crystalrunes.CrystalRunes;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class TeleportPlayerLogic implements Listener {


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        PersistentDataContainer playerData = player.getPersistentDataContainer();


        String teleport = playerData.get(new NamespacedKey(CrystalRunes.getPlugin(), "teleport"), PersistentDataType.STRING);

        if (!(e.getFrom().getBlockX() == e.getTo().getBlockX() && e.getFrom().getBlockY() == e.getTo().getBlockY() && e.getFrom().getBlockZ() == e.getTo().getBlockZ())) {
            playerData.set(new NamespacedKey(CrystalRunes.getPlugin(), "move"), PersistentDataType.STRING, "true");

            if (teleport.equalsIgnoreCase("true")) {
                player.sendMessage(ChatColor.RED + "You Moved!");
                playerData.set(new NamespacedKey(CrystalRunes.getPlugin(), "teleport"), PersistentDataType.STRING, "false");
            }
        }


    }
}