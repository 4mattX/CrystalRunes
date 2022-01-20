package com.matthew4man.crystalrunes.listeners.crystalRuneListeners;

import com.matthew4man.crystalrunes.CrystalRunes;
import com.matthew4man.crystalrunes.methods.CrystalFinder;
import com.matthew4man.crystalrunes.objects.Crystal;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InstantGoldTool implements Listener {

    @EventHandler
    public void onBlockLeftClick(PlayerInteractEvent event) {

        if (!(event.getAction().equals(Action.LEFT_CLICK_BLOCK))) {
            return;
        }

        List<Material> goldTools = new ArrayList<>();
        goldTools.add(Material.GOLDEN_PICKAXE);
        goldTools.add(Material.GOLDEN_SHOVEL);
        goldTools.add(Material.GOLDEN_AXE);
        goldTools.add(Material.GOLDEN_HOE);

        Player player = event.getPlayer();

        if (!(goldTools.contains(player.getInventory().getItemInMainHand().getType()))) {
            return;
        }

        Block block = event.getClickedBlock();

        Location location = block.getLocation();

        Crystal crystal = CrystalFinder.getCrystalFromChunk(location);

        if (crystal == null) {
            return;
        }

        if (!(crystal.getPrimaryRunes().contains(41) || crystal.getSecondaryRunes().contains(41))) {
            return;
        }
        Map<Enchantment, Integer> enchantmentsCopy = player.getInventory().getItemInMainHand().getEnchantments();
        player.getInventory().getItemInMainHand().addUnsafeEnchantment(Enchantment.DIG_SPEED, 1000);

        Bukkit.getScheduler().runTaskLater(CrystalRunes.getPlugin(), () -> {
            player.getInventory().getItemInMainHand().removeEnchantment(Enchantment.DIG_SPEED);
            player.getInventory().getItemInMainHand().addUnsafeEnchantments(enchantmentsCopy);
        }, 1L);

    }

}
