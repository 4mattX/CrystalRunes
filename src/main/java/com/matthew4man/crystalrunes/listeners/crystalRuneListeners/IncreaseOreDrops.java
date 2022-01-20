package com.matthew4man.crystalrunes.listeners.crystalRuneListeners;

import com.matthew4man.crystalrunes.methods.CrystalFinder;
import com.matthew4man.crystalrunes.objects.Crystal;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;

public class IncreaseOreDrops implements Listener {

    @EventHandler
    public void onOreBreak(BlockBreakEvent event) {

        Location location = event.getBlock().getLocation();

        Crystal crystal = CrystalFinder.getCrystalFromChunk(location);

        if (crystal == null) {
            return;
        }

        if (!(crystal.getPrimaryRunes().contains(22) || crystal.getSecondaryRunes().contains(22))) {
            return;
        }

        Block block = event.getBlock();

        List<Material> ores = new ArrayList<>();
        ores.add(Material.COAL_ORE);
        ores.add(Material.DEEPSLATE_COAL_ORE);
        ores.add(Material.IRON_ORE);
        ores.add(Material.DEEPSLATE_IRON_ORE);
        ores.add(Material.COPPER_ORE);
        ores.add(Material.DEEPSLATE_COPPER_ORE);
        ores.add(Material.GOLD_ORE);
        ores.add(Material.DEEPSLATE_GOLD_ORE);
        ores.add(Material.REDSTONE_ORE);
        ores.add(Material.DEEPSLATE_REDSTONE_ORE);
        ores.add(Material.EMERALD_ORE);
        ores.add(Material.DEEPSLATE_EMERALD_ORE);
        ores.add(Material.LAPIS_ORE);
        ores.add(Material.DEEPSLATE_LAPIS_ORE);
        ores.add(Material.NETHER_GOLD_ORE);
        ores.add(Material.NETHER_QUARTZ_ORE);
        ores.add(Material.DIAMOND_ORE);
        ores.add(Material.DEEPSLATE_DIAMOND_ORE);

        if (!ores.contains(block.getType())) {
            return;
        }

        if (!block.hasMetadata("not-natural")) {
            block.getDrops().forEach(drop -> {
                block.getLocation().getWorld().dropItemNaturally(block.getLocation(), drop);
            });
        }



    }

}
