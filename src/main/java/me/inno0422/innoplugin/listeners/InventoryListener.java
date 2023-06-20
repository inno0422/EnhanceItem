package me.inno0422.innoplugin.listeners;

import me.inno0422.innoplugin.Main;
import me.inno0422.innoplugin.inventories.EnchantInventory;
import me.inno0422.innoplugin.inventories.UpgradeInventory;
import me.inno0422.innoplugin.utils.EnchantUtils;
import me.inno0422.innoplugin.utils.UpgradeUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.format.TextDecoration;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Content;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;



public class InventoryListener implements Listener {
    public Inventory targetInventory;
    public int targetSlot;
    public ItemStack targetItemStack;
    Main plugin = Main.plugin;
    @EventHandler
    public void onClickInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getView().getTitle().equals("강화")) {
            event.setCancelled(true);
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null) return;
            if (event.getClickedInventory().equals(event.getInventory())) {
                if (event.getRawSlot() == 22 && event.getInventory().getItem(22) != null) {
                    upgrade((Player) event.getWhoClicked(),event.getClickedInventory() , event.getCurrentItem());
                }
            } else if (event.getInventory().getItem(22) == null && isUpgradable(clickedItem)){
                targetInventory = event.getInventory();
                targetSlot = 22;
                targetItemStack = clickedItem;
                updateTarget();
                clickedItem.setAmount(0);
            } else if (!isUpgradable(clickedItem)) {
                player.sendMessage(ChatColor.RED + "선택한 아이템은 강화 할 수 없습니다.");
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 0.4f, 0.8f);
            }
        }
    }
    @EventHandler
    public void onOpenInventory(InventoryOpenEvent event) {
        if (event.getInventory().getType().equals(InventoryType.ANVIL) && plugin.getConfig().getBoolean("Upgrade.enable")) {
            event.setCancelled(true);
            UpgradeInventory upgradeInventory = new UpgradeInventory();
            Inventory upgradeInv = upgradeInventory.upgradeInv;
            event.getPlayer().openInventory(upgradeInv);
        }
        if (!isCustomInv(event.getView())) {
            return;
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory(); // 닫힌 인벤토리
        Player player = (Player) event.getPlayer(); // 플레이어

        // 인벤토리가 플레이어의 인벤토리이며, 13번째 슬롯에 아이템이 있는지 확인
        if ( ((event.getView().getTitle().equals("강화"))) && inventory.getItem(22) != null) {
            // 13번째 슬롯에 있는 아이템 가져오기
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
    }

    public boolean isCustomInv(InventoryView inventory) {
        String[] name = {"강화", "마법 부여"};
        for (String string : name) {
            if (string.equals(inventory.getTitle())) {
                return true;
            }
        }
        return false;
    }
    private boolean isUpgradable(ItemStack item) {
        String[] name = {"_SWORD", "_HELMET", "_CHESTPLATE", "_LEGGINGS", "_BOOTS"};
        for(String suffix : name) {
            if (item.getType().toString().endsWith(suffix)) {
                return true;
            }
        }
        return false;
    }

    public void upgrade(Player player, Inventory inventory, ItemStack item) {
            ItemMeta itemMeta = item.getItemMeta();
            Random random = new Random();
            List<String> lore = new ArrayList<>();
            int[] successTable = {100, 90, 80, 70, 60, 50, 50, 40, 30, 10};
//            int[] successTable = {100, 100, 100, 100, 100, 100, 100, 100, 100, 100};
            int[] destroyTable = {0, 0, 0, 0, 0, 10, 20, 30, 40, 99};
            int star = UpgradeUtils.countStar(item);
            if(star >= 10) return;
            // [star + 1]이 아니고 [star]인 이유는 배열이라서 +1을 하면 2칸 뒤의 것이 됨.
            lore.add(ChatColor.BOLD + "" + ChatColor.GREEN + "성공 확률 : " + Integer.toString(successTable[star]) + "%");
            lore.add(ChatColor.BOLD + "" + ChatColor.RED + "실패 시 파괴 확률 : " + Integer.toString(destroyTable[star]) + "%");
            if (star + 1 >= 6) lore.add(ChatColor.BOLD + "" + ChatColor.DARK_RED + "실패 시 등급 하락!");
            itemMeta.setLore(lore);
            ItemStack sign = inventory.getItem(24);
            sign.setItemMeta(itemMeta);
            itemMeta = null;

            player.sendMessage(Integer.toString(star));

            if (random.nextInt(100) < successTable[star]) {
                UpgradeUtils.upgrade(item, star + 1);
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 1, 1.5f);

                if(star + 1 >= 10) {
                    itemMeta = item.getItemMeta();
                    itemMeta.setUnbreakable(true);
                    player.sendMessage("전설의" + ChatColor.YELLOW + " " + ChatColor.BOLD + item.getI18NDisplayName() + ChatColor.RESET + "가 탄생했습니다!");
                    itemMeta.setDisplayName(ChatColor.GOLD + player.getDisplayName() + "의 " + item.getI18NDisplayName());
                    item.setItemMeta(itemMeta);
                }
            } else {
                if (random.nextInt(100) < destroyTable[star]) {
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 0.5f, 0.86f);
                    item.setAmount(0);
                } else if (star >= 6) {
                    player.playSound(player.getLocation(), Sound.BLOCK_CHAIN_BREAK, 1.0f, 1.5f);
                    UpgradeUtils.upgrade(item, star - (random.nextInt(2) + 1));
                } else {
                    player.playSound(player.getLocation(), Sound.BLOCK_CHAIN_BREAK, 1.0f, 1.5f);
                }
            }


            return;
    }

    private void updateTarget() {
        targetInventory.setItem(targetSlot, targetItemStack.clone());
    }
}
