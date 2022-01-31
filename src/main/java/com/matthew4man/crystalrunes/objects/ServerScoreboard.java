package com.matthew4man.crystalrunes.objects;

import com.matthew4man.crystalrunes.CrystalRunes;
import com.matthew4man.crystalrunes.fileConfig.CrystalsConfig;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scoreboard.*;

public class ServerScoreboard {

    public static void create(Player player) {

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("server", "dummy", ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "DuplicityPVP");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        PersistentDataContainer playerData = player.getPersistentDataContainer();
        // Create Lives String
        int amtLives = playerData.get(new NamespacedKey(CrystalRunes.getPlugin(), "lives"), PersistentDataType.INTEGER);
        int amtPockets = playerData.get(new NamespacedKey(CrystalRunes.getPlugin(), "pockets"), PersistentDataType.INTEGER);
        int amtDeaths = playerData.get(new NamespacedKey(CrystalRunes.getPlugin(), "deaths"), PersistentDataType.INTEGER);
        int amtKills = playerData.get(new NamespacedKey(CrystalRunes.getPlugin(), "kills"), PersistentDataType.INTEGER);

        String livesString = ChatColor.GRAY + "" + ChatColor.BOLD + ">> " + ChatColor.GREEN + "Lives: ";

        for (int i = 0; i < amtLives; i++) {
            livesString += ChatColor.RED + "❤";
        }

        for (int i = 0; i < (amtPockets - amtLives); i++) {
            livesString += ChatColor.GRAY + "❤";
        }

        Economy economy = CrystalRunes.getEconomy();
        int playerBalance = (int) economy.getBalance(player);
        if (playerBalance < 0) {
            playerBalance = 0;
        }

        int amountCrystals = CrystalRunes.getAmountCrystals(player.getUniqueId().toString());

        Score s1 = objective.getScore(ChatColor.WHITE + "play.duplicitypvp.com");
        Score s2 = objective.getScore("  ");
        Score s3 = objective.getScore(ChatColor.GRAY + "" + ChatColor.BOLD + ">> " + ChatColor.DARK_RED + "Deaths: " + ChatColor.WHITE + amtDeaths);
        Score s4 = objective.getScore(ChatColor.GRAY + "" + ChatColor.BOLD + ">> " + ChatColor.RED + "Kills: " + ChatColor.WHITE + amtKills);
        Score s5 = objective.getScore(" ");
        Score s6 = objective.getScore(livesString);
        Score s7 = objective.getScore(ChatColor.GRAY + "" + ChatColor.BOLD + ">> " + ChatColor.YELLOW + "Crystals: " + ChatColor.WHITE + amountCrystals);
        Score s8 = objective.getScore(ChatColor.GRAY + "" + ChatColor.BOLD + ">> " + ChatColor.GOLD + "Gold: " + ChatColor.WHITE + playerBalance);
        Score s9 = objective.getScore(ChatColor.LIGHT_PURPLE + "=-=-=-=-=-=-=-=-=");

        s1.setScore(1);
        s2.setScore(2);
        s3.setScore(3);
        s4.setScore(4);
        s5.setScore(5);
        s6.setScore(6);
        s7.setScore(7);
        s8.setScore(8);
        s9.setScore(9);

        player.setScoreboard(scoreboard);
    }
}
