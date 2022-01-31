package com.matthew4man.crystalrunes.listeners.playerListeners;

import com.matthew4man.crystalrunes.CrystalRunes;
import com.matthew4man.crystalrunes.objects.PlayerSoul;
import com.matthew4man.crystalrunes.objects.ServerScoreboard;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class PlayerDeath implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player player = (Player) event.getEntity();

        if (player.getWorld().equals(Bukkit.getWorld("spawn"))) {
            return;
        }

        Location location = player.getLocation();

        ItemStack soul = new PlayerSoul().getSoul();

        player.getWorld().dropItem(location, soul);

        PersistentDataContainer playerData = player.getPersistentDataContainer();
        int oldLives = playerData.get(new NamespacedKey(CrystalRunes.getPlugin(), "lives"), PersistentDataType.INTEGER);
        int amtDeaths = playerData.get(new NamespacedKey(CrystalRunes.getPlugin(), "deaths"), PersistentDataType.INTEGER);
        playerData.set(new NamespacedKey(CrystalRunes.getPlugin(), "lives"), PersistentDataType.INTEGER, oldLives - 1);
        playerData.set(new NamespacedKey(CrystalRunes.getPlugin(), "deaths"), PersistentDataType.INTEGER, amtDeaths + 1);

        ServerScoreboard serverScoreboard = new ServerScoreboard();
        serverScoreboard.create(player);

        // Drop amount of gold 2-10%
//        Economy economy = CrystalRunes.getEconomy();
//        Random random = new Random();
//        int randPercent = random.nextInt(8) + 2;
//
//
//        double playerGold = economy.getBalance(player);
//
//        int droppedGold = (int) (playerGold / randPercent);
//
//        if (droppedGold > 10) {
//            economy.withdrawPlayer(player, droppedGold);
//
//            while (droppedGold > 9) {
//                player.getWorld().dropItem(location, new ItemStack(Material.GOLD_BLOCK));
//                playerGold -= 9;
//            }
//
//            while (droppedGold > 0) {
//                player.getWorld().dropItem(location, new ItemStack(Material.GOLD_INGOT));
//                playerGold -= 1;
//            }
//        }
        // Drop amount of gold 10%
        double playerGold = (int) CrystalRunes.getEconomy().getBalance(player);
        if (playerGold > 10) {
            int dropAmount = ((int) playerGold / 10) - 1;

            CrystalRunes.getEconomy().withdrawPlayer(player, dropAmount);

            while (dropAmount >= 0) {
                if (dropAmount >= 9) {
                    player.getWorld().dropItem(location, new ItemStack(Material.GOLD_BLOCK));
                    dropAmount -= 9;
                    continue;
                }
                player.getWorld().dropItem(location, new ItemStack(Material.GOLD_INGOT));
                dropAmount -= 1;
            }
        }

        if (oldLives - 1 <= 0) {

//            LocalDateTime date = LocalDateTime.now().plusHours(4);
            LocalDateTime date = LocalDateTime.now().plusSeconds(20);
            Date banExpire = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());


            Bukkit.getBanList(BanList.Type.NAME).addBan(player.getDisplayName(), ChatColor.RED + "You do not have any more lives!" + ChatColor.DARK_GRAY + " You must wait to regenerate 1 life.", banExpire, "yo mama");
            player.kickPlayer(ChatColor.RED + "Depleted Lives!");
        }

    }

    @EventHandler
    public void onPlayerKillPlayer(EntityDeathEvent event) {

        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        if (!(event.getEntity().getKiller() instanceof Player)) {
            return;
        }

        Player killer = event.getEntity().getKiller();

        PersistentDataContainer playerData = killer.getPersistentDataContainer();
        int amtKills = playerData.get(new NamespacedKey(CrystalRunes.getPlugin(), "kills"), PersistentDataType.INTEGER);
        playerData.set(new NamespacedKey(CrystalRunes.getPlugin(), "kills"), PersistentDataType.INTEGER, amtKills + 1);

        ServerScoreboard serverScoreboard = new ServerScoreboard();
        serverScoreboard.create(killer);

    }

}
