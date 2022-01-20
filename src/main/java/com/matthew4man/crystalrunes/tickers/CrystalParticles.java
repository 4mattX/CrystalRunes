package com.matthew4man.crystalrunes.tickers;

import com.matthew4man.crystalrunes.CrystalRunes;
import com.matthew4man.crystalrunes.objects.Crystal;
import org.bukkit.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class CrystalParticles {

    public void start() {
        new BukkitRunnable() {
            double time = 0;
            double time2 = 0;
            double radius = 1;
            World world;

            public void run() {

                time = time + Math.PI / 8;
                // Helix parametric equation
                double x = radius * cos(time);
                double y = 0.25 * time;
                double z = radius * sin(time);

                time2 = time2 + Math.PI / 8;
                double x2 = radius * sin(time);
                double y2 = 0.25 * time;
                double z2 = radius * cos(time);


                for (List<Crystal> crystalList : CrystalRunes.getCrystalMap().values()) {
                    crystalList.forEach(crystal -> {
                        world = crystal.getWorld();
                        Location location = crystal.getLocation();


                        location.add(x, y, z);

                        int pRune = crystal.getTypePrimary();

                        switch (pRune) {
                            case 0:
                                world.spawnParticle(Particle.REDSTONE, location.getX() + 0.5, location.getY(), location.getZ() + 0.5, 5, 0, 1, 0, 10, new Particle.DustOptions(Color.GRAY, (float) 0.5));
                                break;
                            case 1:
                                world.spawnParticle(Particle.REDSTONE, location.getX() + 0.5, location.getY(), location.getZ() + 0.5, 5, 0, 1, 0, 10, new Particle.DustOptions(Color.RED, (float) 0.5));
                                break;
                            case 2:
                                world.spawnParticle(Particle.REDSTONE, location.getX() + 0.5, location.getY(), location.getZ() + 0.5, 5, 0, 1, 0, 10, new Particle.DustOptions(Color.BLUE, (float) 0.5));
                                break;
                            case 3:
                                world.spawnParticle(Particle.REDSTONE, location.getX() + 0.5, location.getY(), location.getZ() + 0.5, 5, 0, 1, 0, 10, new Particle.DustOptions(Color.LIME, (float) 0.5));
                                break;
                            case 4:
                                world.spawnParticle(Particle.REDSTONE, location.getX() + 0.5, location.getY(), location.getZ() + 0.5, 5, 0, 1, 0, 10, new Particle.DustOptions(Color.YELLOW, (float) 0.5));
                                break;
                            case 5:
                                world.spawnParticle(Particle.REDSTONE, location.getX() + 0.5, location.getY(), location.getZ() + 0.5, 5, 0, 1, 0, 10, new Particle.DustOptions(Color.PURPLE, (float) 0.75));
                                break;
                        }

                        location.subtract(x, y, z);

                        if (y > 2 || y < 0) {
                            time *= -0.25;
                        }
                    });
                }

                for (List<Crystal> crystalList : CrystalRunes.getCrystalMap().values()) {
                    crystalList.forEach(crystal -> {
                        world = crystal.getWorld();
                        Location location = crystal.getLocation();


                        location.add(x2, y2, z2);

                        int sRune = crystal.getTypeSecondary();

                        switch (sRune) {
                            case 0:
                                world.spawnParticle(Particle.REDSTONE, location.getX() + 0.5, location.getY(), location.getZ() + 0.5, 5, 0, 1, 0, 10, new Particle.DustOptions(Color.WHITE, (float) 0.5));
                                break;
                            case 1:
                                world.spawnParticle(Particle.REDSTONE, location.getX() + 0.5, location.getY(), location.getZ() + 0.5, 5, 0, 1, 0, 10, new Particle.DustOptions(Color.RED, (float) 0.5));
                                break;
                            case 2:
                                world.spawnParticle(Particle.REDSTONE, location.getX() + 0.5, location.getY(), location.getZ() + 0.5, 5, 0, 1, 0, 10, new Particle.DustOptions(Color.BLUE, (float) 0.5));
                                break;
                            case 3:
                                world.spawnParticle(Particle.REDSTONE, location.getX() + 0.5, location.getY(), location.getZ() + 0.5, 5, 0, 1, 0, 10, new Particle.DustOptions(Color.LIME, (float) 0.5));
                                break;
                            case 4:
                                world.spawnParticle(Particle.REDSTONE, location.getX() + 0.5, location.getY(), location.getZ() + 0.5, 5, 0, 1, 0, 10, new Particle.DustOptions(Color.YELLOW, (float) 0.5));
                                break;
                            case 5:
                                world.spawnParticle(Particle.REDSTONE, location.getX() + 0.5, location.getY(), location.getZ() + 0.5, 5, 0, 1, 0, 10, new Particle.DustOptions(Color.PURPLE, (float) 0.75));
                                break;
                        }

                        location.subtract(x2, y2, z2);

                        if (y2 > 2 || y2 < 0) {
                            time2 *= -0.25;
                        }

                    });
                }


            }
        }.runTaskTimer(CrystalRunes.getPlugin(), 0, 3); // Updates every 3 ticks
    }
}
