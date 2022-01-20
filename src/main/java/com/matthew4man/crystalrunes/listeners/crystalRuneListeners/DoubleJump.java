package com.matthew4man.crystalrunes.listeners.crystalRuneListeners;

import com.matthew4man.crystalrunes.methods.CrystalFinder;
import com.matthew4man.crystalrunes.objects.Crystal;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;


public class DoubleJump implements Listener {

    @EventHandler
    public void onPlayerSneakJump(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();

        if (!player.isSneaking()) {
            return;
        }

        Location location = player.getLocation();

        Crystal crystal = CrystalFinder.getCrystalFromChunk(location);

        if (crystal == null) {
            return;
        }

        if (!(crystal.getPrimaryRunes().contains(42) || crystal.getSecondaryRunes().contains(42))) {
            return;
        }

        if (!player.getLocation().clone().subtract(0, 1, 0).getBlock().getType().equals(Material.AIR)) {
            return;
        }

        player.setVelocity(player.getLocation().getDirection().multiply(1.5).setY(1));
        player.setFallDistance(0);
        event.setCancelled(true);
    }

}
