package com.matthew4man.crystalrunes.methods;

import com.matthew4man.crystalrunes.CrystalRunes;
import com.matthew4man.crystalrunes.objects.Crystal;
import com.matthew4man.crystalrunes.objects.CrystalChunk;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CrystalFinder {

    public static String findUUID(Location location) {
        Entity crystal = null;

        for (Entity entity : location.getWorld().getNearbyEntities(location, 6, 6, 6)) {
            if (entity.getType() == EntityType.ENDER_CRYSTAL) {
                crystal = entity;
            }
        }

        String crystalUUID = "";

        try {
            PersistentDataContainer crystalData = crystal.getPersistentDataContainer();
            crystalUUID = crystalData.get(new NamespacedKey(CrystalRunes.getPlugin(), "crystalUUID"), PersistentDataType.STRING);
        } catch (Exception e) {
        }

        return crystalUUID;
    }

    public static Crystal findCrystal(Player player) {
        String crystalUUID = findUUID(player.getLocation());

        return CrystalRunes.getCrystal(player.getUniqueId().toString(), crystalUUID);
    }

    public static Crystal getCrystalFromChunk(Location location) {

        int newX = (int) (location.getX() / 16);
        int newZ = (int) (location.getZ() / 16);

        Chunk chunk = location.getWorld().getChunkAt(newX, newZ);

        HashMap chunkMap = CrystalRunes.getCrystalChunkMap();

        Iterator iterator = chunkMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) iterator.next();

            List<CrystalChunk> crystalChunkList = (List<CrystalChunk>) mapElement.getValue();

            for (int i = 0; i < crystalChunkList.size(); i++) {
                if (crystalChunkList.get(i).getChunk().getX() == chunk.getX() && crystalChunkList.get(i).getChunk().getZ() == chunk.getZ()) {
                    return crystalChunkList.get(i).getCrystal();
                }
            }
        }

        return null;
    }

}
