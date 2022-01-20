package com.matthew4man.crystalrunes.listeners.crystalRuneListeners;

import com.matthew4man.crystalrunes.methods.CrystalFinder;
import com.matthew4man.crystalrunes.objects.Crystal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AntiPVP implements Listener {

    @EventHandler
    public void onPlayerHitPlayer(EntityDamageByEntityEvent event) {

        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        Crystal crystal = CrystalFinder.getCrystalFromChunk(event.getEntity().getLocation());

        if (crystal == null) {
            return;
        }

        if (!(crystal.getPrimaryRunes().contains(14) || crystal.getSecondaryRunes().contains(14))) {
            return;
        }

        event.setCancelled(true);

    }
}
