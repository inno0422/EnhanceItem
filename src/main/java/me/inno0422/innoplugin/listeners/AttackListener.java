package me.inno0422.innoplugin.listeners;

import me.inno0422.innoplugin.Main;
import me.inno0422.innoplugin.utils.UpgradeUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class AttackListener implements Listener {
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;
        Player player = (Player) event.getDamager();
        if (UpgradeUtils.countStar(player.getInventory().getItemInMainHand()) == 10) {
            Bukkit.getScheduler().runTaskLaterAsynchronously(Main.plugin, ()->{
                ((LivingEntity) event.getEntity()).setNoDamageTicks(0); // after 100ms you will set the no damage ticks to 0 so arrows can hurt again
            }, 2L);
        }
    }
}
