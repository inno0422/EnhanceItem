package me.inno0422.innoplugin.Items;

import me.inno0422.innoplugin.utils.UpgradeUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static me.inno0422.innoplugin.utils.UpgradeUtils.starCount;

public class Sword {
    public static boolean isSword(ItemStack item) {
        if (item.getType().toString().endsWith("SWORD")) return true;
        return false;
    }
    public static void setLore(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW + UpgradeUtils.numtoStar(starCount));
        lore.add(ChatColor.GRAY + "주로 사용하는 손에 있을 때:");
        lore.add(ChatColor.DARK_GREEN + " " + (Integer.toString((int) getDamage(item))) + " 공격 피해");
        lore.add(ChatColor.DARK_GREEN + " " + (Double.toString(getAttackSpeed() + 2.4)) + " 공격 속도");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }
    public static double getDamage(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        int defaultDamage = 0;
        double sharpness;
        int sharpnesslevel = 0;
        if(item.getType().equals(Material.WOODEN_SWORD)) defaultDamage = 4;
        if(item.getType().equals(Material.STONE_SWORD)) defaultDamage = 5;
        if(item.getType().equals(Material.IRON_SWORD)) defaultDamage = 6;
        if(item.getType().equals(Material.GOLDEN_SWORD)) defaultDamage = 4;
        if(item.getType().equals(Material.DIAMOND_SWORD)) defaultDamage = 7;
        if(item.getType().equals(Material.NETHERITE_SWORD)) defaultDamage = 8;
        if(itemMeta.hasEnchant(Enchantment.DAMAGE_ALL)) sharpnesslevel = itemMeta.getEnchantLevel(Enchantment.DAMAGE_ALL);
        sharpness = sharpnesslevel * 0.5 + 0.5;
        return defaultDamage + sharpness - 0.5;
    }
    public static double getAttackSpeed() {
        return UpgradeUtils.starCount * 0.1 - 2.4;
    }
    public static void attribute(ItemStack item) {
        UpgradeUtils.addAttribute(item, "generic.attackDamage", getDamage(item), Attribute.GENERIC_ATTACK_DAMAGE, EquipmentSlot.HAND);
        UpgradeUtils.addAttribute(item, "generic.attackSpeed", getAttackSpeed(), Attribute.GENERIC_ATTACK_SPEED, EquipmentSlot.HAND);
    }
}
