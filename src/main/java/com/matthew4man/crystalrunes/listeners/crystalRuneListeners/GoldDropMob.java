package com.matthew4man.crystalrunes.listeners.crystalRuneListeners;

import com.matthew4man.crystalrunes.methods.CrystalFinder;
import com.matthew4man.crystalrunes.objects.Crystal;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class GoldDropMob implements Listener {

    @EventHandler
    public void onMobKill(EntityDeathEvent event) {

        Entity entity = event.getEntity();

        Location location = entity.getLocation();

        Crystal crystal = CrystalFinder.getCrystalFromChunk(location);

        if (crystal == null) {
            return;
        }

        if (!(crystal.getPrimaryRunes().contains(25) || crystal.getSecondaryRunes().contains(25))) {
            return;
        }

        event.getDrops().forEach(drop -> {
            Random random = new Random();
            int randomNumber = random.nextInt(4 - 1 + 1) + 1;

            if (randomNumber == 4) {
                entity.getLocation().getWorld().dropItemNaturally(entity.getLocation(), new ItemStack(Material.GOLD_NUGGET));
            }
        });

    }

}
