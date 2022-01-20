package com.matthew4man.crystalrunes.listeners.crystalRuneListeners;

import com.matthew4man.crystalrunes.methods.CrystalFinder;
import com.matthew4man.crystalrunes.objects.Crystal;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.SpawnerSpawnEvent;

public class SpawnerUse implements Listener {

    @EventHandler
    public void onMobSpawn(SpawnerSpawnEvent event) {

        Location location = event.getLocation();

        Crystal crystal = CrystalFinder.getCrystalFromChunk(location);

        if (crystal == null) {
            event.setCancelled(true);
            return;
        }

        if (!(crystal.getPrimaryRunes().contains(45) || crystal.getSecondaryRunes().contains(45))) {
            event.setCancelled(true);
            return;
        }

    }

}
