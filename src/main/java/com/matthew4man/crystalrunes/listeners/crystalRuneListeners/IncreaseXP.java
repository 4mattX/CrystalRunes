package com.matthew4man.crystalrunes.listeners.crystalRuneListeners;

import com.matthew4man.crystalrunes.methods.CrystalFinder;
import com.matthew4man.crystalrunes.objects.Crystal;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;

public class IncreaseXP implements Listener {

    @EventHandler
    public void onExperiencePickUp(PlayerExpChangeEvent event) {

        Player player = event.getPlayer();

        Crystal crystal = CrystalFinder.getCrystalFromChunk(player.getLocation());

        if (crystal == null) {
            return;
        }

        if (!(crystal.getPrimaryRunes().contains(20) || crystal.getSecondaryRunes().contains(20))) {
            return;
        }

        player.giveExp(event.getAmount());
    }

}
