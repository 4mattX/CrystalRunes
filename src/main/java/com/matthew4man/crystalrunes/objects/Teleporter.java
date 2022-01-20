package com.matthew4man.crystalrunes.objects;

import com.matthew4man.crystalrunes.CrystalRunes;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Teleporter {

    Player player;
    Location location;
    int timer = 60;
    int degree = 0;

    Plugin plugin = CrystalRunes.getPlugin();

    public Teleporter(Player player, Location location) {
        this.player = player;
        this.location = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
    }

    public Teleporter(Player player) {
        this.player = player;
    }

    public void spawnTeleport() {

        PersistentDataContainer playerData = player.getPersistentDataContainer();
        playerData.set(new NamespacedKey(CrystalRunes.getPlugin(), "teleport"), PersistentDataType.STRING, "true");
        playerData.set(new NamespacedKey(CrystalRunes.getPlugin(), "move"), PersistentDataType.STRING, "false");

        player.sendMessage(ChatColor.LIGHT_PURPLE + "Teleporting in 3 seconds!");
        player.sendMessage(ChatColor.DARK_PURPLE + "Do not move!");

        new BukkitRunnable() {
            @Override
            public void run() {

                String move = playerData.get(new NamespacedKey(CrystalRunes.getPlugin(), "move"), PersistentDataType.STRING);
                String teleport = playerData.get(new NamespacedKey(CrystalRunes.getPlugin(), "teleport"), PersistentDataType.STRING);

                if (move.equalsIgnoreCase("false") && teleport.equalsIgnoreCase("true")) {


                    String command = "spawn " + player.getName();

                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);

                    landEffect();
                    player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 2, (float) 1.5);
                }

                playerData.set(new NamespacedKey(CrystalRunes.getPlugin(), "teleport"), PersistentDataType.STRING, "false");
            }
        }.runTaskLater(plugin, timer);
    }

    public void teleport() {

        PersistentDataContainer playerData = player.getPersistentDataContainer();
        playerData.set(new NamespacedKey(CrystalRunes.getPlugin(), "teleport"), PersistentDataType.STRING, "true");
        playerData.set(new NamespacedKey(CrystalRunes.getPlugin(), "move"), PersistentDataType.STRING, "false");

        player.sendMessage(ChatColor.LIGHT_PURPLE + "Teleporting in 3 seconds!");
        player.sendMessage(ChatColor.DARK_PURPLE + "Do not move!");

        new BukkitRunnable() {
            @Override
            public void run() {

                String move = playerData.get(new NamespacedKey(CrystalRunes.getPlugin(), "move"), PersistentDataType.STRING);
                String teleport = playerData.get(new NamespacedKey(CrystalRunes.getPlugin(), "teleport"), PersistentDataType.STRING);

                if (move.equalsIgnoreCase("false") && teleport.equalsIgnoreCase("true")) {
                    player.teleport(location);

//                    player.teleport(location);
                    landEffect();
                    player.getWorld().playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 2, (float) 1.5);
                }

                playerData.set(new NamespacedKey(CrystalRunes.getPlugin(), "teleport"), PersistentDataType.STRING, "false");
            }
        }.runTaskLater(plugin, timer);
    }

    public void teleportEffect() {

        new BukkitRunnable() {
            double time = 0;
            double radius = 1;
            PersistentDataContainer playerData = player.getPersistentDataContainer();

            Location effectLocation = player.getLocation();

            public void run() {

                time = time + Math.PI / 8;
                // Helix parametric equation
                double x = radius * cos(time);
                double y = 0.125 * time;
                double z = radius * sin(time);

                effectLocation.add(x, y, z);

                player.getWorld().spawnParticle(Particle.FALLING_OBSIDIAN_TEAR, effectLocation, 5);
//                player.getWorld().playSound(effectLocation, Sound.BLOCK_NOTE_BLOCK_CHIME, 2, (float) x);
                player.getWorld().playSound(effectLocation, Sound.BLOCK_NOTE_BLOCK_BIT, 2, (float) 0.5);

                effectLocation.subtract(x, y, z);

                // Condition to end loop
                if ((playerData.get(new NamespacedKey(CrystalRunes.getPlugin(), "teleport"), PersistentDataType.STRING)).equals("false")) {
                    this.cancel();
                }

            }
        }.runTaskTimer(CrystalRunes.getPlugin(), 0, 1); // Updates every tick
    }

    public void landEffect() {
        new BukkitRunnable() {
            double time = 0;
            double radius = 1;

            PersistentDataContainer playerData = player.getPersistentDataContainer();

            Location effectLocation = player.getLocation().add(0, 2, 0);

            public void run() {

                time = time + Math.PI / 3;
                // Helix parametric equation
                double x = radius * cos(time);
                double y = -0.125 * time;
                double z = radius * sin(time);

                effectLocation.add(x, y, z);

                player.getWorld().spawnParticle(Particle.SPELL_WITCH, effectLocation, 5);

                effectLocation.subtract(x, y, z);

                // Condition to end loop
                if (time > Math.PI * 8) {
                    this.cancel();
                }

            }
        }.runTaskTimer(CrystalRunes.getPlugin(), 0, 1); // Updates every tick
    }

}