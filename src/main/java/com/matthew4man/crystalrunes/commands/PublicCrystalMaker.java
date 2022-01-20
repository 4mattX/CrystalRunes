package com.matthew4man.crystalrunes.commands;

import com.matthew4man.crystalrunes.methods.CrystalFinder;
import com.matthew4man.crystalrunes.objects.Crystal;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PublicCrystalMaker implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!command.getName().equalsIgnoreCase("makecrystalpublic")) {
            return false;
        }

        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;
        Crystal crystal = CrystalFinder.findCrystal(player);

        if (crystal == null) {
            player.sendMessage(ChatColor.RED + "No Crystal Found Nearby");
            return false;
        }

        makePublic(crystal);
        player.sendMessage("Crystal Made Public!");

        return true;
    }

    public void makePublic(Crystal crystal) {
        crystal.setOwnerName(ChatColor.LIGHT_PURPLE + "Server Crystal");
        crystal.setTypePrimary(5);
        crystal.setTypeSecondary(5);

    }
}
