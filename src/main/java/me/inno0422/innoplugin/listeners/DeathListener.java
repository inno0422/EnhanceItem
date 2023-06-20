package me.inno0422.innoplugin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DeathListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        // 플레이어의 모든 아이템 삭제
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null) {
                item.setAmount(0);
            }
        }

        // 플레이어의 갑옷 아이템 삭제
        for (ItemStack item : player.getInventory().getArmorContents()) {
            if (item != null) {
                item.setAmount(0);
            }
        }
    }
}
