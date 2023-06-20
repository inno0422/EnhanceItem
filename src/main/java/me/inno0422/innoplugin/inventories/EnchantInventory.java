package me.inno0422.innoplugin.inventories;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class EnchantInventory {
    public Inventory inventory;
    public EnchantInventory() {
        inventory = Bukkit.createInventory(null, 45, "마법 부여");
    }
}
