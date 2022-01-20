package com.matthew4man.crystalrunes.listeners.crystalRuneListeners;

import com.matthew4man.crystalrunes.methods.CrystalFinder;
import com.matthew4man.crystalrunes.objects.Crystal;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class IncreaseMobDrops implements Listener {

    @EventHandler
    public void onMobKill(EntityDeathEvent event) {

        Entity entity = event.getEntity();

        Location location = entity.getLocation();

        Crystal crystal = CrystalFinder.getCrystalFromChunk(location);

        if (crystal == null) {
            return;
        }

        if (!(crystal.getPrimaryRunes().contains(23) || crystal.getSecondaryRunes().contains(23))) {
            return;
        }

        event.getDrops().forEach(drop -> {
            location.getWorld().dropItemNaturally(location, drop);
        });

    }


}
