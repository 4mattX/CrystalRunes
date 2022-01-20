package com.matthew4man.crystalrunes.listeners.playerListeners;

import com.matthew4man.crystalrunes.CrystalRunes;
import com.matthew4man.crystalrunes.menus.MenuMachine;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class FriendInteract implements Listener {

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEntityEvent event) {

        if (!(event.getRightClicked() instanceof Player))
            return;

        if (!(event.getPlayer().isSneaking()))
            return;

        Player player = event.getPlayer();
        Player otherPlayer = (Player) event.getRightClicked();

        PersistentDataContainer data = player.getPersistentDataContainer();
        data.set(new NamespacedKey(CrystalRunes.getPlugin(), "playerSelectUUID"), PersistentDataType.STRING, otherPlayer.getUniqueId().toString());

        MenuMachine.openMenu(player, "friend-menu", otherPlayer);
    }

//    @EventHandler
//    public void onFriendInvite(InventoryClickEvent event) {
//
//
//        if (event.getView().getTitle().contains("-=-=")) {
//
//            Player player = (Player) event.getWhoClicked();
//            PersistentDataContainer data = player.getPersistentDataContainer();
//
//            String stringRequest = data.get(new NamespacedKey(CrystalRunes.getPlugin(), "playerSelect"), PersistentDataType.STRING);
//            Player otherPlayer = Bukkit.getServer().getPlayer(stringRequest);
//
//            ItemStack clickedItem = event.getCurrentItem();
//
//            if (clickedItem.getType().equals(null)) {
//                return;
//            }
//
//            if (clickedItem.getType().equals(Material.PLAYER_HEAD)) {
//                try {
//                    if (FriendConfig.get().getString(player.getUniqueId().toString() + ".friends").contains(otherPlayer.getUniqueId().toString())) {
//                        unFriend(player, otherPlayer);
//                    } else {
//
//                        // Tests to make sure player does not have the maximum amt of friends
//                        List<String> friendList = FriendConfig.get().getStringList(player.getUniqueId().toString() + ".friends");
//                        int amtFriends = friendList.size();
//
//                        if (amtFriends >= 45) {
//                            player.sendMessage(ChatColor.RED + "You have reached the maximum amount of friends '45'");
//                            return;
//                        }
//
//                        sendInvite(player, otherPlayer);
//                    }
//                } catch (Exception e) {
//                    sendInvite(player, otherPlayer);
//                }
//            }
//
//            if (clickedItem.getType().equals(Material.ENDER_CHEST)) {
//                try {
//                    player.performCommand("trade " + otherPlayer.getDisplayName());
//                    player.closeInventory();
//                } catch (Exception e) {
//                }
//            }
//
//            event.setCancelled(true);
//        }
//    }
//
//    public void sendInvite(Player sender, Player receiver) {
//        sender.sendMessage(ChatColor.GOLD + "You have sent a friend request to " + ChatColor.WHITE + receiver.getDisplayName());
//        sender.closeInventory();
//        receiver.sendMessage(ChatColor.WHITE + sender.getDisplayName() + ChatColor.GOLD + " has sent you a friend request!");
//
//        TextComponent message = new TextComponent("Click here to accept");
//        message.setColor(ChatColor.GREEN);
//        message.setUnderlined(true);
//        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend " + sender.getUniqueId()));
//        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
//                new ComponentBuilder("ʕっ•ᴥ•ʔっ").color(ChatColor.GOLD).create()));
//        receiver.spigot().sendMessage(message);
//    }
//
//    public void unFriend(Player sender, Player receiver) {
//        sender.sendMessage(ChatColor.RED + "You have un-friended " + ChatColor.WHITE + receiver.getDisplayName() + ChatColor.RED + "!");
//        sender.closeInventory();
//        receiver.sendMessage(ChatColor.WHITE + sender.getDisplayName() + ChatColor.RED + " has un-friended you!");
//
//        FriendConfig.removeFriend(sender.getUniqueId().toString(), receiver.getUniqueId().toString());
//        FriendConfig.removeFriend(receiver.getUniqueId().toString(), sender.getUniqueId().toString());
//        FriendConfig.save();
//        FriendConfig.reload();
//    }
}
