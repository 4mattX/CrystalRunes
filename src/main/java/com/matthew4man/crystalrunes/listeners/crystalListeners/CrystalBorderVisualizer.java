package com.matthew4man.crystalrunes.listeners.crystalListeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
import com.matthew4man.crystalrunes.CrystalRunes;
import com.matthew4man.crystalrunes.methods.CrystalFinder;
import com.matthew4man.crystalrunes.objects.Crystal;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;

public class CrystalBorderVisualizer implements Listener {

    @EventHandler
    public void onPlayerRightClickShovel(PlayerInteractEntityEvent event) throws InvocationTargetException {

        Player player = event.getPlayer();

        if (!(event.getRightClicked().getType().equals(EntityType.ENDER_CRYSTAL))) {
            return;
        }

        if (!player.isSneaking()) {
            return;
        }

        Entity crystalEntity = event.getRightClicked();
        Crystal crystal = CrystalFinder.findCrystal(player);

        if (crystal == null) {
            return;
        }

        int xSign = 1;
        int zSign = 1;

        if (crystalEntity.getLocation().getX() < 0) {
            xSign *= -1;
        }

        if (crystalEntity.getLocation().getZ() < 0) {
            zSign *= -1;
        }

        int minX = Math.abs((int) (crystalEntity.getLocation().getX() / 16));
        int minZ = Math.abs((int) (crystalEntity.getLocation().getZ() / 16));
        double x = ((minX * 16) + 8) * xSign;
        double z = ((minZ * 16) + 8) * zSign;

        Particle.DustOptions particle = null;

        WorldBorderApi worldBorderApi = CrystalRunes.getWorldBorderApi();

        Location location = new Location(player.getWorld(), x, 0, z);

        worldBorderApi.setBorder(player, 48, location);

        PersistentDataContainer playerData = player.getPersistentDataContainer();
        playerData.set(new NamespacedKey(CrystalRunes.getPlugin(), "borderBoolean"), PersistentDataType.INTEGER, 1);

        Bukkit.getScheduler().runTaskLater(CrystalRunes.getPlugin(), () -> {
            playerData.set(new NamespacedKey(CrystalRunes.getPlugin(), "borderBoolean"), PersistentDataType.INTEGER, 0);
            worldBorderApi.resetWorldBorderToGlobal(player);
        }, 200L);

    }


}
