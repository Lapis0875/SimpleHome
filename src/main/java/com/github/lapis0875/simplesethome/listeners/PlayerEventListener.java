package com.github.lapis0875.simplesethome.listeners;

import com.github.lapis0875.simplesethome.Constants;
import com.github.lapis0875.simplesethome.Waypoint;
import com.github.lapis0875.simplesethome.WaypointDataType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEventListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        Location location = player.getLocation();
        Waypoint waypoint = new Waypoint(Constants.DEATH_LOCATION, location);
        player.getPersistentDataContainer().set(
            waypoint.getNamespacedKey(),
            new WaypointDataType(),
            waypoint
        );
        player.sendMessage(Component.text(
                String.format(
                        "사망 위치 (%s) %f, %f, %f가 저장되었습니다!",
                        location.getWorld().getEnvironment().name(),
                        location.getX(),
                        location.getY(),
                        location.getZ()
                ),
                TextColor.color(171, 242, 0)
        ));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        player.sendMessage(Component.text(
            "이 서버는 SimpleHome 플러그인을 사용중입니다. /waypoint 로 편리한 셋홈과 사망 위치로의 귀환을 사용해보세요!",
            TextColor.color(171, 242, 0)
        ));
    }
}
