package com.matthew4man.crystalrunes.listeners.crystalRuneListeners;

import com.matthew4man.crystalrunes.CrystalRunes;
import com.matthew4man.crystalrunes.methods.CrystalFinder;
import com.matthew4man.crystalrunes.objects.Crystal;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class BottleXP implements Listener {

    @EventHandler
    public void onBottleRightClick(PlayerInteractEvent event) {

        if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR))) {
            return;
        }

        Player player = event.getPlayer();

        if (!(player.getInventory().getItemInMainHand().getType().equals(Material.GLASS_BOTTLE))) {
            return;
        }

        Location location = player.getLocation();

        Crystal crystal = CrystalFinder.getCrystalFromChunk(location);

        if (crystal == null) {
            return;
        }

        if (!(crystal.getPrimaryRunes().contains(35) || crystal.getSecondaryRunes().contains(35))) {
            return;
        }

        int amountXP = getPlayerExp(player);

        if (amountXP == 0) {
            return;
        }

        ItemStack newBottle = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta newBottleMeta = newBottle.getItemMeta();
        newBottleMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Stored Experience Bottle");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Amount: " + ChatColor.GOLD + ((int) amountXP));
        newBottleMeta.setLore(lore);

        PersistentDataContainer bottleData = newBottleMeta.getPersistentDataContainer();
        bottleData.set(new NamespacedKey(CrystalRunes.getPlugin(), "amount-xp"), PersistentDataType.INTEGER, amountXP);

        newBottle.setItemMeta(newBottleMeta);
        player.setLevel(0);
        player.setExp(0);

        if (player.getInventory().getItemInMainHand().getAmount() == 1) {
            player.getInventory().addItem(newBottle);
        }

        // If inventory is not full
        else if (player.getInventory().firstEmpty() != -1) {
            player.getInventory().addItem(newBottle);
        }

        else {
            player.getWorld().dropItemNaturally(player.getLocation(), newBottle);
        }



        for (ItemStack itemStack : player.getInventory()) {
            if (itemStack == null) {
                continue;
            }

            if (itemStack.getType().equals(Material.GLASS_BOTTLE)) {

                if (itemStack.getAmount() == 0) {
                    itemStack.setType(Material.AIR);
                    return;
                }

                itemStack.setAmount(itemStack.getAmount() - 1);
                return;
            }
        }

    }

    @EventHandler
    public void onXPBottleRightClick(PlayerInteractEvent event) {

        if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR))) {
            return;
        }

        Player player = event.getPlayer();

        if (player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getItemMeta() == null || player.getInventory().getItemInMainHand().getItemMeta().getLore() == null) {
            return;
        }

        if (!(player.getInventory().getItemInMainHand().getItemMeta().getLore().get(0).contains("Amount:"))) {
            return;
        }

        event.setCancelled(true);

        ItemStack xpBottle = player.getInventory().getItemInMainHand();
        PersistentDataContainer xpBottleData = xpBottle.getItemMeta().getPersistentDataContainer();
        int amountXP = xpBottleData.get(new NamespacedKey(CrystalRunes.getPlugin(), "amount-xp"), PersistentDataType.INTEGER);

        player.getInventory().setItemInMainHand(new ItemStack(Material.GLASS_BOTTLE));
        changePlayerExp(player, amountXP);

    }

    public static int getExpToLevelUp(int level){
        if(level <= 15){
            return 2*level+7;
        } else if(level <= 30){
            return 5*level-38;
        } else {
            return 9*level-158;
        }
    }

    // Calculate total experience up to a level
    public static int getExpAtLevel(int level){
        if(level <= 16){
            return (int) (Math.pow(level,2) + 6*level);
        } else if(level <= 31){
            return (int) (2.5*Math.pow(level,2) - 40.5*level + 360.0);
        } else {
            return (int) (4.5*Math.pow(level,2) - 162.5*level + 2220.0);
        }
    }

    // Calculate player's current EXP amount
    public static int getPlayerExp(Player player){
        int exp = 0;
        int level = player.getLevel();

        // Get the amount of XP in past levels
        exp += getExpAtLevel(level);

        // Get amount of XP towards next level
        exp += Math.round(getExpToLevelUp(level) * player.getExp());

        return exp;
    }

    public static int changePlayerExp(Player player, int exp){
        // Get player's current exp
        int currentExp = getPlayerExp(player);

        // Reset player's current exp to 0
        player.setExp(0);
        player.setLevel(0);

        // Give the player their exp back, with the difference
        int newExp = currentExp + exp;
        player.giveExp(newExp);

        // Return the player's new exp amount
        return newExp;
    }


}
