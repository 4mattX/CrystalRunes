package com.matthew4man.crystalrunes.listeners.playerListeners;

import com.matthew4man.crystalrunes.CrystalRunes;
import com.matthew4man.crystalrunes.objects.PlayerSoul;
import com.matthew4man.crystalrunes.objects.ServerScoreboard;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerConsumeSoul implements Listener {

    @EventHandler
    public void onSoulConsume(PlayerItemConsumeEvent event) {

        Player player = event.getPlayer();

        ItemStack soul = new PlayerSoul().getSoul();

        if (event.getItem().isSimilar(soul)) {
            PersistentDataContainer playerData = player.getPersistentDataContainer();
            int amtLives = playerData.get(new NamespacedKey(CrystalRunes.getPlugin(), "lives"), PersistentDataType.INTEGER);
            int amtPockets = playerData.get(new NamespacedKey(CrystalRunes.getPlugin(), "pockets"), PersistentDataType.INTEGER);

            if (amtLives >= amtPockets) {
                player.sendMessage(ChatColor.RED + "You have already reached your max amount of lives");
                event.setCancelled(true);
                return;
            }

            playerData.set(new NamespacedKey(CrystalRunes.getPlugin(), "lives"), PersistentDataType.INTEGER, amtLives + 1);

            ServerScoreboard serverScoreboard = new ServerScoreboard();
            serverScoreboard.create(player);

            player.sendMessage(ChatColor.GREEN + "+1 Life");
        }
    }

}