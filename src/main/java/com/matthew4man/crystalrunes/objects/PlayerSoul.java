package com.matthew4man.crystalrunes.objects;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class PlayerSoul {

    public static ItemStack getSoul() {
        ItemStack soul = new ItemStack(Material.HONEY_BOTTLE, 1);

        ItemMeta soulMeta = soul.getItemMeta();

        ArrayList<String> soulLore = new ArrayList<>();
        soulLore.add(ChatColor.DARK_GRAY + "Consume to gain one life");

        soulMeta.setDisplayName(ChatColor.AQUA + "+1 Life");
        soulMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
        soulMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        soulMeta.setLore(soulLore);

        soul.setItemMeta(soulMeta);

        return soul;
    }

}