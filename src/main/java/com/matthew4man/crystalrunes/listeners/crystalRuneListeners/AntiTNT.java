package com.matthew4man.crystalrunes.listeners.crystalRuneListeners;

import com.matthew4man.crystalrunes.methods.CrystalFinder;
import com.matthew4man.crystalrunes.objects.Crystal;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;

public class AntiTNT implements Listener {

    @EventHandler
    public void onTNTExplosion(EntityExplodeEvent event) {

        if (!(event.getEntity() instanceof TNTPrimed)) {
            return;
        }

        List<Block> blockList = event.blockList();

        blockList.forEach(block -> {
            Location location = block.getLocation();

            Crystal crystal = CrystalFinder.getCrystalFromChunk(location);

            if (crystal == null) {
                return;
            }

            if (!(crystal.getPrimaryRunes().contains(10) || crystal.getSecondaryRunes().contains(10))) {
                return;
            }

            event.setCancelled(true);
        });
    }

}
