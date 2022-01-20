package com.matthew4man.crystalrunes.listeners.crystalRuneListeners;

import com.matthew4man.crystalrunes.methods.CrystalFinder;
import com.matthew4man.crystalrunes.objects.Crystal;
import org.bukkit.Location;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.inventory.FurnaceStartSmeltEvent;
import org.bukkit.inventory.ItemStack;

public class FurnaceSpeed implements Listener {

    @EventHandler
    public void FurnaceStartSmelt(FurnaceSmeltEvent event) {

        Location location = event.getBlock().getLocation();

        Crystal crystal = CrystalFinder.getCrystalFromChunk(location);

        if (crystal == null) {
            return;
        }

        if (!(crystal.getPrimaryRunes().contains(34) || crystal.getSecondaryRunes().contains(34))) {
            return;
        }

        Furnace furnace = (Furnace) event.getBlock().getState();

        int amountRaw = furnace.getInventory().getSmelting().getAmount();
        int amountCooked = furnace.getInventory().getResult().getAmount();

        try {

            if (amountRaw >= 2) {

                furnace.getInventory().getSmelting().setAmount(amountRaw - 2);
                furnace.getInventory().getResult().setAmount(amountCooked + 2);

                event.setCancelled(true);
            }
        } catch (Exception e) {

        }

    }

}
