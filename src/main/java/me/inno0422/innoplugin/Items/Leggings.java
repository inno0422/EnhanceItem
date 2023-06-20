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

public class Leggings {
    public static boolean isLeggings(ItemStack item) {
        if (item.getType().toString().endsWith("LEGGINGS")) return true;
        return false;
    }
    public static void setLore(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.YELLOW + UpgradeUtils.numtoStar(starCount));
        lore.add(ChatColor.GRAY + "다리에 있을 때:");
        lore.add(ChatColor.BLUE + " +" +(Integer.toString((int)getArmor(item))) + " 방어");
        lore.add(ChatColor.BLUE + " +" +(Integer.toString((int)getArmorToughness(item))) + " 방어 강도");
        lore.add(ChatColor.BLUE + " +" + (Integer.toString(getHealth())) + " 체력");
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }
    public static int getHealth() {
        return starCount * 2;
    }
    public static double getArmor(ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        int defaultArmor = 0;
        int protectionlevel = 0;
        double protection = 0;
        if(item.getType().equals(Material.LEATHER_LEGGINGS)) defaultArmor = 2;
        if(item.getType().equals(Material.CHAINMAIL_LEGGINGS)) defaultArmor = 4;
        if(item.getType().equals(Material.IRON_LEGGINGS)) defaultArmor = 5;
        if(item.getType().equals(Material.GOLDEN_LEGGINGS)) defaultArmor = 3;
        if(item.getType().equals(Material.DIAMOND_LEGGINGS)) defaultArmor = 6;
        if(item.getType().equals(Material.NETHERITE_LEGGINGS)) defaultArmor = 6;
        if(itemMeta.hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) protectionlevel = itemMeta.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
        protection = (protectionlevel * 4) / 100;
        return defaultArmor + defaultArmor * protection;
    }
    public static double getArmorToughness(ItemStack item) {
        int defaultArmor = 0;
        if(item.getType().equals(Material.DIAMOND_LEGGINGS)) defaultArmor = 2;
        if(item.getType().equals(Material.NETHERITE_LEGGINGS)) defaultArmor = 3;
        return defaultArmor;
    }

    public static void attribute(ItemStack item) {
        UpgradeUtils.addAttribute(item, "generic.armor", getArmor(item), Attribute.GENERIC_ARMOR, EquipmentSlot.LEGS);
        UpgradeUtils.addAttribute(item, "generic.armorToughness", getArmorToughness(item), Attribute.GENERIC_ARMOR_TOUGHNESS, EquipmentSlot.LEGS);
        UpgradeUtils.addAttribute(item, "generic.maxHealth", getHealth(), Attribute.GENERIC_MAX_HEALTH, EquipmentSlot.LEGS);
    }
}
