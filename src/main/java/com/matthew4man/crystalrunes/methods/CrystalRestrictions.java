package com.matthew4man.crystalrunes.methods;

import com.matthew4man.crystalrunes.CrystalRunes;
import com.matthew4man.crystalrunes.objects.Crystal;
import com.matthew4man.crystalrunes.objects.CrystalChunk;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CrystalRestrictions {

    public static boolean isSafe(Player player, Location location) {
        List<Chunk> crystalChunkList = new ArrayList<>();
        int[] offset = {-1, 0, 1};

        for (int xOffset : offset) {
            for (int zOffset : offset) {
                Chunk crystalChunk =  player.getWorld().getChunkAt((int) (location.getX() / 16) + xOffset, (int) (location.getZ() / 16) + zOffset);
                crystalChunkList.add(crystalChunk);
            }
        }

        final boolean[] safe = {true};

        for (List<CrystalChunk> ccList : CrystalRunes.getCrystalChunkMap().values()) {
            ccList.forEach(x -> {
                crystalChunkList.forEach(y -> {
                    if (x.getChunk().getX() == y.getX() && x.getChunk().getZ() == y.getZ()) {
                        if (x.getChunk().getWorld().toString().equals(y.getWorld().toString())) {
                            safe[0] = false;

                        }

                    }
                });
            });
        }

        return safe[0];
    }

}
