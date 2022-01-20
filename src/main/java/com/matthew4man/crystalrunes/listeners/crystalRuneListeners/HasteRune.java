package com.matthew4man.crystalrunes.listeners.crystalRuneListeners;

import com.matthew4man.crystalrunes.methods.CrystalFinder;
import com.matthew4man.crystalrunes.objects.Crystal;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HasteRune implements Listener {

    @EventHandler
    public void onBlockLeftClick(PlayerInteractEvent event) {

        if (!(event.getAction().equals(Action.LEFT_CLICK_BLOCK))) {
            return;
        }

        Block block = event.getClickedBlock();

        Location location = block.getLocation();

        Crystal crystal = CrystalFinder.getCrystalFromChunk(location);

        if (crystal == null) {
            return;
        }

        if (!(crystal.getPrimaryRunes().contains(40) || crystal.getSecondaryRunes().contains(40))) {
            return;
        }

        Player player = event.getPlayer();

        player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 40, 2));

    }

}
