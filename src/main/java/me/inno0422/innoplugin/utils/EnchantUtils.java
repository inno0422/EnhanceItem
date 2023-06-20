package me.inno0422.innoplugin.utils;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;
import java.util.Random;

public class EnchantUtils {
    public static boolean isEnchantable(ItemStack item) {
        if (item == null) {
            return false;
        }

        for (Enchantment enchantment : Enchantment.values()) {
            if (enchantment.canEnchantItem(item)) {
                return true;
            }
        }

        return false;
    }
    public static void enchant(ItemStack item) {
        int numEnchants = item.getEnchantments().size() == 0 ? 1 : item.getEnchantments().size();
        if (new Random().nextInt(15) == 0 && numEnchants < 7) numEnchants++;
        for (Enchantment enchantment : item.getEnchantments().keySet()) {
            item.removeEnchantment(enchantment);
        }

        // 인첸트 추가
        for (int i = 1; i <= numEnchants; i++) {
            Enchantment enchantment = getRandomEnchantment();
            if (!item.getEnchantments().containsKey(enchantment)) {
                int level = getRandomEnchantmentLevel(enchantment);
                item.addUnsafeEnchantment(enchantment, level);
            } else i--;
        }
    }

    public static Enchantment getRandomEnchantment() {
        Enchantment[] enchantments = Enchantment.values();
        return enchantments[new Random().nextInt(enchantments.length)];
    }
    public static int getRandomEnchantmentLevel(Enchantment enchantment) {
        int minLevel = enchantment.getStartLevel();
        int maxLevel = enchantment.getMaxLevel();

        if (minLevel == maxLevel) {
            return minLevel;
        } else {
            for (int i = maxLevel; i > 1; i--) {
                if (new Random().nextInt(i * 3) == 0) {
                    return i;
                }
            }
            return 1;
        }
    }

}
