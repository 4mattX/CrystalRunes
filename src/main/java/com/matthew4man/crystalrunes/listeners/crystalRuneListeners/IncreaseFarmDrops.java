package com.matthew4man.crystalrunes.listeners.crystalRuneListeners;

import com.matthew4man.crystalrunes.methods.CrystalFinder;
import com.matthew4man.crystalrunes.objects.Crystal;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class IncreaseFarmDrops implements Listener {

    @EventHandler
    public void onCropHarvest(BlockBreakEvent event) {

        Location location = event.getBlock().getLocation();

        Crystal crystal = CrystalFinder.getCrystalFromChunk(location);

        if (crystal == null) {
            return;
        }

        if (!(crystal.getPrimaryRunes().contains(21) || crystal.getSecondaryRunes().contains(21))) {
            return;
        }

        Block block = event.getBlock();

        List<Material> crops = new ArrayList<>();
        crops.add(Material.WHEAT);
        crops.add(Material.BEETROOTS);
        crops.add(Material.CARROTS);
        crops.add(Material.POTATOES);
        crops.add(Material.BAMBOO);
        crops.add(Material.COCOA_BEANS);
        crops.add(Material.KELP_PLANT);
        crops.add(Material.SEA_PICKLE);
        crops.add(Material.NETHER_WART);
        crops.add(Material.GLOW_BERRIES);

        if (!crops.contains(block.getType())) {
            return;
        }

        try {
            Ageable ageable = (Ageable) block.getState().getBlockData();

            if (ageable.getAge() == ageable.getMaximumAge()) {
                block.getDrops().forEach(drop -> {
                    block.getLocation().getWorld().dropItemNaturally(block.getLocation(), drop);
                });
            }
        } catch (Exception e) {
        }

    }

    @EventHandler
    public void onBlockCropHarvest(BlockBreakEvent event) {

        Location location = event.getBlock().getLocation();

        Crystal crystal = CrystalFinder.getCrystalFromChunk(location);

        if (crystal == null) {
            return;
        }

        if (!(crystal.getPrimaryRunes().contains(21) || crystal.getSecondaryRunes().contains(21))) {
            return;
        }

        Block block = event.getBlock();

        List<Material> crops = new ArrayList<>();
        crops.add(Material.MELON);
        crops.add(Material.PUMPKIN);
        crops.add(Material.CACTUS);
        crops.add(Material.SUGAR_CANE);

        if (!crops.contains(block.getType())) {
            return;
        }

        Block testBlock = block;

        while (testBlock.getType().equals(block.getType())) {
            if (!testBlock.hasMetadata("not-natural")) {
                block.getDrops().forEach(drop -> {
                    block.getLocation().getWorld().dropItemNaturally(block.getLocation(), drop);
                });
            } else {
                return;
            }
            testBlock = testBlock.getLocation().add(0, 1, 0).getBlock();
        }


    }


}
