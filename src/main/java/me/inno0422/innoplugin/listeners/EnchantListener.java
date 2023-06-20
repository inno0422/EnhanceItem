package me.inno0422.innoplugin.listeners;

import me.inno0422.innoplugin.Main;
import me.inno0422.innoplugin.inventories.EnchantInventory;
import me.inno0422.innoplugin.utils.EnchantUtils;
import me.inno0422.innoplugin.utils.UpgradeUtils;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class EnchantListener implements Listener {
    Main plugin = Main.plugin;
    Inventory targetInventory;
    int targetSlot;
    ItemStack targetItemStack;
    @EventHandler
    public void onClickInv(InventoryClickEvent event) {
        if (!event.getView().getTitle().equalsIgnoreCase("마법 부여")) return;
        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);
        ItemStack clickedItem = event.getCurrentItem();
        if (event.getClickedInventory().equals(event.getInventory())) {
            if (event.getRawSlot() == 22 && event.getInventory().getItem(22) != null) {
                EnchantUtils.enchant(clickedItem);
                player.playSound(player.getLocation(), Sound.BLOCK_CHAIN_BREAK, 1.0f, 1.5f);
            }
        } else if (event.getInventory().getItem(22) == null && EnchantUtils.isEnchantable(clickedItem) && UpgradeUtils.countStar(clickedItem) == 0){
            targetInventory = event.getInventory();
            targetSlot = 22;
            targetItemStack = clickedItem;
            updateTarget();
            clickedItem.setAmount(0);
        } else if (!EnchantUtils.isEnchantable(clickedItem)) {
            player.sendMessage(ChatColor.RED + "선택한 아이템은 인첸트 할 수 없습니다.");
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 0.4f, 0.8f);
        } else if (UpgradeUtils.countStar(clickedItem) != 0) {
            player.sendMessage(ChatColor.RED + "강화한 아이템은 인첸트 할 수 없습니다.");
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 0.4f, 0.8f);
        }
    }
    @EventHandler
    public void onOpenInv(InventoryOpenEvent event) {
        if (event.getInventory().getType().equals(InventoryType.ENCHANTING) && plugin.getConfig().getBoolean("CustomEnchant")) {
            event.setCancelled(true);
            EnchantInventory enchantInventory = new EnchantInventory();
            Inventory enchantInv = enchantInventory.inventory;
            event.getPlayer().openInventory(enchantInv);
        }
    }
    @EventHandler
    public void onCloseInv(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getPlayer();
        if (!event.getView().getTitle().equalsIgnoreCase("마법 부여") || inventory.getItem(22) == null) return;
        ItemStack item = inventory.getItem(22).clone();

        // 플레이어의 인벤토리로 아이템 이동
        HashMap<Integer, ItemStack> leftoverItems = player.getInventory().addItem(item);

        // 남은 아이템 처리
        if (!leftoverItems.isEmpty()) {
            for (ItemStack leftover : leftoverItems.values()) {
                // 남은 아이템을 드롭 또는 다른 처리를 수행할 수 있습니다.
                player.getWorld().dropItem(player.getLocation(), leftover);
            }
        }
    }
    private void updateTarget() {
        targetInventory.setItem(targetSlot, targetItemStack.clone());
    }
}
