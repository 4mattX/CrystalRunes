package com.matthew4man.crystalrunes.listeners.crystalRuneListeners;

import com.matthew4man.crystalrunes.CrystalRunes;
import com.matthew4man.crystalrunes.menus.MenuMachine;
import com.matthew4man.crystalrunes.methods.CrystalFinder;
import com.matthew4man.crystalrunes.objects.Crystal;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CobbleGenerator implements Listener {

    @EventHandler
    public void onCobbleGenerate(BlockFromToEvent event) {

        Location location = event.getToBlock().getLocation();

        Crystal crystal = CrystalFinder.getCrystalFromChunk(location);

        if (crystal == null) {
            return;
        }

        if (!(crystal.getPrimaryRunes().contains(44) || crystal.getSecondaryRunes().contains(44))) {
            return;
        }

        Material type = event.getBlock().getType();
        if (type == Material.WATER || type == Material.LEGACY_STATIONARY_WATER || type == Material.LAVA || type == Material.LEGACY_STATIONARY_LAVA){
            Block b = event.getToBlock();
            if (b.getType() == Material.AIR){
                if (generatesCobble(type, b)){
                    /* DO WHATEVER YOU NEED WITH THE COBBLE */

                    Material chosenMaterial = null;

                    Random random = new Random();
                    int randomNumber = random.nextInt(16 - 1 + 1) + 1;

                    List<Material> oreList = new ArrayList<>();
                    oreList.add(Material.COPPER_ORE);
                    oreList.add(Material.COAL_ORE);
                    oreList.add(Material.IRON_ORE);
                    oreList.add(Material.GOLD_ORE);
                    oreList.add(Material.REDSTONE_ORE);
                    oreList.add(Material.EMERALD_ORE);
                    oreList.add(Material.DIAMOND_ORE);
                    oreList.add(Material.LAPIS_ORE);

                    Location dustLocation = b.getLocation().add(0.5, 1, 0.5);

                    if (randomNumber == 16) {
                        Random randomOre = new Random();
                        int randomOreNumber = randomOre.nextInt(oreList.size() - 0 + 0) + 0;
                        chosenMaterial = oreList.get(randomOreNumber);
                    }

                    b.getWorld().spawnParticle(Particle.SMOKE_LARGE, dustLocation.getX(), dustLocation.getY(), dustLocation.getZ(), 8, 0.25, 0, 0.25, 0.01);

                    if (chosenMaterial == null) {
                        chosenMaterial = Material.COBBLESTONE;
                    }

                    b.setType(chosenMaterial);

                    b.getWorld().playSound(b.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 0.5f, 2);

                    event.setCancelled(true);
                }
            }
        }


    }

    private final BlockFace[] faces = new BlockFace[]{
            BlockFace.SELF,
            BlockFace.UP,
            BlockFace.DOWN,
            BlockFace.NORTH,
            BlockFace.EAST,
            BlockFace.SOUTH,
            BlockFace.WEST
    };

    public boolean generatesCobble(Material type, Block b){
        Material mirrorID1 = (type == Material.WATER || type == Material.LEGACY_STATIONARY_WATER ? Material.LAVA : Material.WATER);
        Material mirrorID2 = (type == Material.WATER || type == Material.LEGACY_STATIONARY_WATER ? Material.LEGACY_STATIONARY_LAVA : Material.LEGACY_STATIONARY_WATER);
        for (BlockFace face : faces){
            Block r = b.getRelative(face, 1);
            if (r.getType() == mirrorID1 || r.getType() == mirrorID2){
                return true;
            }
        }
        return false;
    }

}
