package com.matthew4man.crystalrunes.listeners.crystalListeners;

import com.matthew4man.crystalrunes.CrystalRunes;
import com.matthew4man.crystalrunes.methods.CrystalRestrictions;
import com.matthew4man.crystalrunes.methods.RandomNameGenerator;
import com.matthew4man.crystalrunes.objects.Crystal;
import com.matthew4man.crystalrunes.objects.CrystalChunk;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class CrystalPlace implements Listener {

    @EventHandler
    public void onCrystalPlace(BlockPlaceEvent event) {

        Player player = event.getPlayer();

        ItemStack item = event.getItemInHand();
        ItemMeta itemMeta = item.getItemMeta();

        try {
            if (!itemMeta.getLore().get(0).contains("Crystal")) {
                return;
            }
        } catch (Exception e) {
            return;
        }

        // If single item
        if (item.getAmount() == 1) {
            player.getInventory().getItemInHand().setType(Material.AIR);
        } else {
            player.getInventory().getItemInHand().setAmount(player.getInventory().getItemInHand().getAmount() - 1);
        }

        if (!CrystalRestrictions.isSafe(player, event.getBlock().getLocation())) {
            player.sendMessage(ChatColor.RED + "You are too close to an existing " + ChatColor.LIGHT_PURPLE + "Crystal" + ChatColor.RED + "!");
            event.setCancelled(true);
            return;
        }

        generateCrystal(player, event.getBlockPlaced().getLocation());
    }

    public void generateCrystal(Player player, Location location) {

        String playerUUID = player.getUniqueId().toString();
        String crystalUUID = UUID.randomUUID().toString();
        String crystalName = RandomNameGenerator.generateName();

        EnderCrystal crystalEntity = (EnderCrystal) player.getWorld().spawnEntity(location.add(0.5, 0, 0.5), EntityType.ENDER_CRYSTAL);
        PersistentDataContainer entityData = crystalEntity.getPersistentDataContainer();
        entityData.set(new NamespacedKey(CrystalRunes.getPlugin(), "crystalUUID"), PersistentDataType.STRING, crystalUUID);
        entityData.set(new NamespacedKey(CrystalRunes.getPlugin(), "playerUUID"), PersistentDataType.STRING, playerUUID);
        entityData.set(new NamespacedKey(CrystalRunes.getPlugin(), "name"), PersistentDataType.STRING, crystalName);
        crystalEntity.setShowingBottom(false);

        location.getBlock().setType(Material.AIR);

        // Generate Crystal Object
        Crystal crystal = new Crystal(playerUUID, player.getName(), crystalUUID, location);
        crystal.setOwnerName(player.getName());
        crystal.setName(crystalName);
        CrystalRunes.addCrystal(playerUUID, crystal);
        updateChunkMap(player, location, crystal);

    }

    public void updateChunkMap(Player player, Location location, Crystal crystal) {

        int[] offset = {-1, 0, 1};

        for (int xOffset : offset) {
            for (int zOffset : offset) {

                Chunk chunk = player.getWorld().getChunkAt((int) (location.getX() / 16) + xOffset, (int) (location.getZ() / 16) + zOffset);
                CrystalChunk crystalChunk = new CrystalChunk(chunk, crystal);
                CrystalRunes.addCrystalChunk(player.getUniqueId().toString(), crystalChunk);
            }
        }


    }


}
