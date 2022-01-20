package com.matthew4man.crystalrunes.listeners.crystalRuneListeners;

import com.matthew4man.crystalrunes.methods.CrystalFinder;
import com.matthew4man.crystalrunes.objects.Crystal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class NoFallDamage implements Listener {

    @EventHandler
    public void onPlayerFallDamage(EntityDamageEvent event) {

        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();

        if (!(event.getCause().equals(EntityDamageEvent.DamageCause.FALL))) {
            return;
        }

        Crystal crystal = CrystalFinder.getCrystalFromChunk(player.getLocation());

        if (crystal == null) {
            return;
        }

        if (!(crystal.getPrimaryRunes().contains(15) || crystal.getSecondaryRunes().contains(15))) {
            return;
        }

        event.setCancelled(true);
    }

}
