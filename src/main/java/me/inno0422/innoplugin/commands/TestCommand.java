package me.inno0422.innoplugin.commands;

import me.inno0422.innoplugin.inventories.EnchantInventory;
import me.inno0422.innoplugin.inventories.UpgradeInventory;
import me.inno0422.innoplugin.utils.EnchantUtils;
import me.inno0422.innoplugin.utils.UpgradeUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Player player = (Player) commandSender;
        if (strings.length < 1) return false;
        String subcommand = strings[0];
//        if (subcommand.equalsIgnoreCase("upgrade")) {
//            UpgradeInventory upgradeInventory = new UpgradeInventory();
//            Inventory upgradeInv = upgradeInventory.upgradeInv;
//            player.openInventory(upgradeInv);
//            return true;
//        } else if (subcommand.equalsIgnoreCase("enhance")) {
        if (subcommand.equalsIgnoreCase("enhance")) {
            if (strings[1] == null) return false;
            ItemStack item = player.getInventory().getItemInMainHand();
            UpgradeUtils.upgrade(item, Integer.parseInt(strings[1]));
            return true;
        } else if (subcommand.equalsIgnoreCase("enchant")) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (EnchantUtils.isEnchantable(item)) {
                player.sendMessage("Yes");
                EnchantUtils.enchant(item);
            }
            else player.sendMessage("No");
        } else if (subcommand.equalsIgnoreCase("enchanto")) {
            EnchantInventory enchantInventory = new EnchantInventory();
            Inventory enchantInv = enchantInventory.inventory;
            player.openInventory(enchantInv);
            return true;
        }
        return false;
    }
}
