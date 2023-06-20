package me.inno0422.innoplugin.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UpgradeInventory {
    public Inventory upgradeInv;
    public UpgradeInventory() {
        upgradeInv = Bukkit.createInventory(null, 45, "강화");
        ItemStack stainedGlass = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta stainedGlassMeta = stainedGlass.getItemMeta();
        stainedGlassMeta.setDisplayName(" ");
        stainedGlass.setItemMeta(stainedGlassMeta);
        ItemStack sign = new ItemStack(Material.OAK_SIGN);
        upgradeInv.setItem(24, sign);
        for (int slot = 0; slot < upgradeInv.getSize(); slot++) {
            if (slot != 22 && slot != 24) {
                upgradeInv.setItem(slot, stainedGlass);
            }
        }
    }
}
