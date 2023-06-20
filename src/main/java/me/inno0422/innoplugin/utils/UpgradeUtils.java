package me.inno0422.innoplugin.utils;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import me.inno0422.innoplugin.Items.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.A;

import java.util.*;

public class UpgradeUtils {
    public static int starCount = 0;


    public static void upgrade(ItemStack item, int star) {
        starCount = star;
        List<String> lore = null;
        if (Sword.isSword(item)) {
            Sword.setLore(item); Sword.attribute(item);
        } else if (Helmet.isHelmet(item)){
            Helmet.setLore(item); Helmet.attribute(item);
        } else if (Chestplate.isChestPlate(item)){
            Chestplate.setLore(item); Chestplate.attribute(item);
        } else if (Leggings.isLeggings(item)) {
            Leggings.setLore(item); Leggings.attribute(item);
        } else if (Boots.isBoots(item)){
            Boots.setLore(item); Boots.attribute(item);
        } else return;

    }

    public static String numtoStar(int num) {
        String star = "";
        for (int i = 1; i <= num; i++) {
            star = star + "★";
        }
        for (int i = 1; i <= 10 - num; i++) {
            star = star + "☆";
        }
        return star;
    }
    public static int countStar(ItemStack item) {
        int starCount = 0; // ★ 개수를 저장할 변수

        if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
            List<String> lore = item.getItemMeta().getLore();
            for (String line : lore) {
                int count = line.length() - line.replace("★", "").length();
                starCount += count;
            }
        }
        return starCount;
    }

    public static void addAttribute(ItemStack item, String name, double amount, Attribute attribute, EquipmentSlot equipmentSlot) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.removeAttributeModifier(attribute);
        AttributeModifier modifier = new AttributeModifier(UUID.randomUUID(), name, amount, AttributeModifier.Operation.ADD_NUMBER, equipmentSlot);
        System.out.println(name);
        itemMeta.addAttributeModifier(attribute, modifier);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.setItemMeta(itemMeta);
    }
}
