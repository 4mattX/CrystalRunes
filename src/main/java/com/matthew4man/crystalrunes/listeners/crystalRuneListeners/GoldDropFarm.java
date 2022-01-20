package com.matthew4man.crystalrunes.listeners.crystalRuneListeners;

import com.matthew4man.crystalrunes.methods.CrystalFinder;
import com.matthew4man.crystalrunes.objects.Crystal;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GoldDropFarm implements Listener {

    @EventHandler
    public void onCropHarvest(BlockBreakEvent event) {

        Location location = event.getBlock().getLocation();

        Crystal crystal = CrystalFinder.getCrystalFromChunk(location);

        if (crystal == null) {
            return;
        }

        if (!(crystal.getPrimaryRunes().contains(24) || crystal.getSecondaryRunes().contains(24))) {
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



                Random random = new Random();
                int randomNumber = random.nextInt(8 - 1 + 1) + 1;

                if (randomNumber == 8) {
                    block.getLocation().getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.GOLD_NUGGET));
                }
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

        if (!(crystal.getPrimaryRunes().contains(24) || crystal.getSecondaryRunes().contains(24))) {
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
                Random random = new Random();
                int randomNumber = random.nextInt(8 - 1 + 1) + 1;

                if (randomNumber == 8) {
                    block.getLocation().getWorld().dropItemNaturally(block.getLocation(), new ItemStack(Material.GOLD_NUGGET));
                }
            } else {
                return;
            }
            testBlock = testBlock.getLocation().add(0, 1, 0).getBlock();
        }
    }

}
