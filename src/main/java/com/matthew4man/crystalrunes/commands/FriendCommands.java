package com.matthew4man.crystalrunes.commands;

import com.matthew4man.crystalrunes.CrystalRunes;
import com.matthew4man.crystalrunes.objects.Crystal;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class FriendCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Actually friends someone no one can run this command
        if (command.getName().equalsIgnoreCase("friend")) {
            Player player = (Player) sender;

            String playerUUID = player.getUniqueId().toString();
            String friendUUID = args[0];
            String friendName = Bukkit.getPlayer(UUID.fromString(friendUUID)).getName();
            Player friend = Bukkit.getPlayer(UUID.fromString(friendUUID));

            if (!CrystalRunes.isFriends(playerUUID, friendUUID)) {
                CrystalRunes.addFriend(playerUUID, friendUUID);
            } else {
                player.sendMessage(ChatColor.RED + "You are already friends with " + ChatColor.GOLD + friendName);
                return false;
            }

            player.sendMessage(ChatColor.GOLD + "You have accepted " + ChatColor.WHITE + friendName + "'s " + ChatColor.GOLD + "friend request!");
            friend.sendMessage(ChatColor.WHITE + player.getName() + ChatColor.GOLD + " has accepted your request!");

        }

        // Unfriends someone, anybody can run this command
        if (command.getName().equalsIgnoreCase("unfriend")) {
//            Player player = (Player) sender;
//
//            String playerUUID = player.getUniqueId().toString();
//            String friendUUID = args[0];
//            String friendName = Bukkit.getPlayer(UUID.fromString(friendUUID)).getName();
//            Player friend = Bukkit.getPlayer(UUID.fromString(friendUUID));
//
//            if (CrystalRunes.isFriends(playerUUID, friendUUID)) {
//                CrystalRunes.removeFriend(playerUUID, friendUUID);
//            } else {
//                player.sendMessage(ChatColor.RED + "You are not friends with " + ChatColor.GOLD + friendName);
//                return false;
//            }
//
//            player.sendMessage(ChatColor.RED + "You have removed " + ChatColor.WHITE + friendName + ChatColor.GOLD + "from your friend list!");
//            friend.sendMessage(ChatColor.RED + player.getName() + ChatColor.GOLD + " has removed you as a friend!");
            getServer().getScheduler().runTaskAsynchronously(CrystalRunes.getPlugin(), new Runnable() {
                @Override
                public void run() {

                    Player player = (Player) sender;

                    String playerUUID = (player.getUniqueId().toString());

                    String friendUUID = null;

                    OfflinePlayer friend = null;

                    try {
                        friend = Bukkit.getOfflinePlayer(args[0]);
                        friendUUID = String.valueOf(friend.getUniqueId());

                    } catch (Exception e) {
                    }

                    if (!CrystalRunes.isFriends(playerUUID, friendUUID)) {
                        player.sendMessage(ChatColor.RED + "You are not friends with " + ChatColor.WHITE + args[0]);
                        return;

                    }

                    CrystalRunes.removeFriend(playerUUID, friendUUID);

                    player.sendMessage(ChatColor.RED + "You have un-friended " + ChatColor.WHITE + args[0] + ChatColor.RED + "!");

                    try {
                        Player onlineFriend = (Player) friend;
                        onlineFriend.sendMessage(ChatColor.WHITE + player.getDisplayName() + ChatColor.RED + " has un-friended you!");
                    } catch (Exception e) {
                    }

                }

            });

        }
        return false;
    }
}
