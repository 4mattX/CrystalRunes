package com.matthew4man.crystalrunes.listeners.crystalRuneListeners;

import com.matthew4man.crystalrunes.methods.CrystalFinder;
import com.matthew4man.crystalrunes.objects.Crystal;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.List;

public class AnimalHealth implements Listener {

    @EventHandler
    public void onMonsterHit(EntityDamageByEntityEvent event) {

        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        List<EntityType> animalList = new ArrayList<>();
        animalList.add(EntityType.BAT);
        animalList.add(EntityType.CAT);
        animalList.add(EntityType.CHICKEN);
        animalList.add(EntityType.COD);
        animalList.add(EntityType.COW);
        animalList.add(EntityType.DONKEY);
        animalList.add(EntityType.FOX);
        animalList.add(EntityType.GLOW_SQUID);
        animalList.add(EntityType.HORSE);
        animalList.add(EntityType.MUSHROOM_COW);
        animalList.add(EntityType.MULE);
        animalList.add(EntityType.OCELOT);
        animalList.add(EntityType.PARROT);
        animalList.add(EntityType.PUFFERFISH);
        animalList.add(EntityType.RABBIT);
        animalList.add(EntityType.SALMON);
        animalList.add(EntityType.SHEEP);
        animalList.add(EntityType.SKELETON_HORSE);
        animalList.add(EntityType.SNOWMAN);
        animalList.add(EntityType.SQUID);
        animalList.add(EntityType.STRIDER);
        animalList.add(EntityType.TROPICAL_FISH);
        animalList.add(EntityType.TURTLE);
        animalList.add(EntityType.VILLAGER);
        animalList.add(EntityType.WANDERING_TRADER);
        animalList.add(EntityType.LLAMA);
        animalList.add(EntityType.IRON_GOLEM);
        animalList.add(EntityType.BEE);
        animalList.add(EntityType.PIG);

        if (!(animalList.contains(event.getEntity().getType()))) {
            return;
        }

        Location location = event.getEntity().getLocation();

        Crystal crystal = CrystalFinder.getCrystalFromChunk(location);

        if (crystal == null) {
            return;
        }

        if (!(crystal.getPrimaryRunes().contains(33) || crystal.getSecondaryRunes().contains(33))) {
            return;
        }

        LivingEntity entity = (LivingEntity) event.getEntity();
        entity.setGlowing(true);
        entity.setHealth(0);
    }

}
