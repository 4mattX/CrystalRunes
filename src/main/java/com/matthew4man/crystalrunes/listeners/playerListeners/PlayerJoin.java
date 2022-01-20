package com.matthew4man.crystalrunes.listeners.playerListeners;

import com.matthew4man.crystalrunes.CrystalRunes;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

//        String command = "spawn " + player.getName();
//
//        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);

        // Check if player has correct persistent data
        PersistentDataContainer playerData = player.getPersistentDataContainer();

        try {
            int amtLives = playerData.get(new NamespacedKey(CrystalRunes.getPlugin(), "lives"), PersistentDataType.INTEGER);
            int amtPockets = playerData.get(new NamespacedKey(CrystalRunes.getPlugin(), "pockets"), PersistentDataType.INTEGER);
            int amtKills = playerData.get(new NamespacedKey(CrystalRunes.getPlugin(), "kills"), PersistentDataType.INTEGER);
            int amtDeaths = playerData.get(new NamespacedKey(CrystalRunes.getPlugin(), "deaths"), PersistentDataType.INTEGER);

            if (amtLives <= 0) {
                playerData.set(new NamespacedKey(CrystalRunes.getPlugin(), "lives"), PersistentDataType.INTEGER, 1);
            }


        } catch (NullPointerException e) {
            playerData.set(new NamespacedKey(CrystalRunes.getPlugin(), "lives"), PersistentDataType.INTEGER, 2);
            playerData.set(new NamespacedKey(CrystalRunes.getPlugin(), "pockets"), PersistentDataType.INTEGER, 3);
            playerData.set(new NamespacedKey(CrystalRunes.getPlugin(), "kills"), PersistentDataType.INTEGER, 0);
            playerData.set(new NamespacedKey(CrystalRunes.getPlugin(), "deaths"), PersistentDataType.INTEGER, 0);
        }
        playerData.set(new NamespacedKey(CrystalRunes.getPlugin(), "teleport"), PersistentDataType.STRING, "false");
        playerData.set(new NamespacedKey(CrystalRunes.getPlugin(), "borderBoolean"), PersistentDataType.INTEGER, 0);



//        ServerScoreboard serverScoreboard = new ServerScoreboard();
//        serverScoreboard.create(event.getPlayer());
    }
}
