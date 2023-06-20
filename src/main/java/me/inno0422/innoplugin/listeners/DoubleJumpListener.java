package me.inno0422.innoplugin.listeners;

import me.inno0422.innoplugin.Main;
import me.inno0422.innoplugin.utils.UpgradeUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;

import static me.inno0422.innoplugin.Main.plugin;

public class DoubleJumpListener implements Listener {


    private final double horizontalBoostFactor = 1.0; // 좌우로 나아갈 힘의 배수
    private final double verticalBoostFactor = 0.5; // 위로 뜨는 힘의 배수

    @EventHandler
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();

        // 서바이벌 모드에서만 동작
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }
        event.setCancelled(true);
        if (!hasCustomBoots(player)) {
            return;
        }

        // 플레이어가 점프 중인지 확인
        if (!player.isOnGround()) {
            // 플레이어의 머리 위에 있는 블록 확인
            Block headBlock = player.getLocation().add(0, 2, 0).getBlock();
            if (headBlock.getType() != Material.AIR) {
                // 더블 점프 취소
                event.setCancelled(true);
                return;
            }

            // 땅에 닿기 전에 다시 점프하여 앞으로 튀어나가기
            Vector velocity = player.getVelocity();
            Vector direction = player.getLocation().getDirection().setY(0).normalize();
            velocity.add(direction.multiply(horizontalBoostFactor)); // 좌우로 나아갈 힘 추가
            velocity.setY(Math.max(velocity.getY() + verticalBoostFactor, verticalBoostFactor)); // 위로 뜨는 힘 추가
            player.setVelocity(velocity);

            // 점프 취소
            event.setCancelled(true);
            player.setFlying(false);
            player.setAllowFlight(false);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        // 서바이벌 모드에서만 동작
        if (player.getGameMode() != GameMode.SURVIVAL) {
            return;
        }
        if (!hasCustomBoots(player)) {
            player.setAllowFlight(false);
            return;
        }

        // 플레이어가 땅에 닿았을 때 다시 점프 가능하게 설정
        if (player.isOnGround() && hasCustomBoots(player)) {
            player.setAllowFlight(true);
        }
    }
    public boolean hasCustomBoots(Player player) {
        ItemStack boots = player.getInventory().getBoots();

        // 플레이어가 신고 있는 신발 아이템이 null이 아니고, 원하는 아이템이면 true 반환
        return boots != null && boots.getType().toString().endsWith("_BOOTS") && (UpgradeUtils.countStar(boots) >= 10);
    }
}