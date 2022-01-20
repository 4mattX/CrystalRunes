package com.matthew4man.crystalrunes.listeners.crystalRuneListeners;

import com.matthew4man.crystalrunes.methods.CrystalFinder;
import com.matthew4man.crystalrunes.objects.Crystal;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.ArrayList;
import java.util.List;

public class MonsterHealth implements Listener {

    @EventHandler
    public void onMonsterHit(EntityDamageByEntityEvent event) {

        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        List<EntityType> monsterList = new ArrayList<>();
        monsterList.add(EntityType.BLAZE);
        monsterList.add(EntityType.CREEPER);
        monsterList.add(EntityType.DROWNED);
        monsterList.add(EntityType.ELDER_GUARDIAN);
        monsterList.add(EntityType.ENDERMITE);
        monsterList.add(EntityType.EVOKER);
        monsterList.add(EntityType.GHAST);
        monsterList.add(EntityType.GUARDIAN);
        monsterList.add(EntityType.HOGLIN);
        monsterList.add(EntityType.HUSK);
        monsterList.add(EntityType.MAGMA_CUBE);
        monsterList.add(EntityType.PHANTOM);
        monsterList.add(EntityType.PIGLIN_BRUTE);
        monsterList.add(EntityType.PILLAGER);
        monsterList.add(EntityType.RAVAGER);
        monsterList.add(EntityType.SHULKER);
        monsterList.add(EntityType.SILVERFISH);
        monsterList.add(EntityType.SKELETON);
        monsterList.add(EntityType.SLIME);
        monsterList.add(EntityType.STRAY);
        monsterList.add(EntityType.SPIDER);
        monsterList.add(EntityType.VEX);
        monsterList.add(EntityType.CAVE_SPIDER);
        monsterList.add(EntityType.VINDICATOR);
        monsterList.add(EntityType.WITCH);
        monsterList.add(EntityType.WITHER_SKELETON);
        monsterList.add(EntityType.ZOGLIN);
        monsterList.add(EntityType.ZOMBIE);
        monsterList.add(EntityType.ZOMBIE_VILLAGER);
        monsterList.add(EntityType.ENDERMAN);

        if (!(monsterList.contains(event.getEntity().getType()))) {
            return;
        }

        Location location = event.getEntity().getLocation();

        Crystal crystal = CrystalFinder.getCrystalFromChunk(location);

        if (crystal == null) {
            return;
        }

        if (!(crystal.getPrimaryRunes().contains(32) || crystal.getSecondaryRunes().contains(32))) {
            return;
        }

        LivingEntity entity = (LivingEntity) event.getEntity();
        entity.setGlowing(true);
        entity.setHealth(0);

    }
}
