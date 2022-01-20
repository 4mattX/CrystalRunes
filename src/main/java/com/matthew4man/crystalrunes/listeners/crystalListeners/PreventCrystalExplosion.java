package com.matthew4man.crystalrunes.listeners.crystalListeners;

import com.matthew4man.crystalrunes.methods.CrystalDeleter;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PreventCrystalExplosion implements Listener {

    @EventHandler
    public void onCrystalHurt(EntityDamageEvent event) {

        if (event.getEntity().getWorld().getName().equals("world_the_end")) {
            return;
        }

        if (event.getEntity().getType().equals(EntityType.ENDER_CRYSTAL)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerHitCrystal(EntityDamageByEntityEvent event) {

        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        if (!(event.getEntity() instanceof EnderCrystal)) {
            return;
        }

        Player player = (Player) event.getDamager();

        if (player.getInventory().getItemInMainHand().getType().equals(Material.BARRIER)) {

            try {
                CrystalDeleter.delete(event.getEntity());
            } catch (NoClassDefFoundError e) {
                event.getEntity().remove();
            }
        }
    }

    @EventHandler
    public void onExplosionCrystal(EntityDamageByEntityEvent event) {

        if (event.getEntity().getWorld().getName().equals("world_the_end")) {
            return;
        }

        if (!(event.getDamager() instanceof Creeper) || !(event.getDamager() instanceof TNTPrimed)) {
            return;
        }

        if (!(event.getEntity() instanceof EnderCrystal)) {
            return;
        }

        event.setCancelled(true);
    }

}
