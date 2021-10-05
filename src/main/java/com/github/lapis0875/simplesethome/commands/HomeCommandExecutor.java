package com.github.lapis0875.simplesethome.commands;

import com.github.lapis0875.simplesethome.Constants;
import com.github.lapis0875.simplesethome.SimpleHome;
import com.github.lapis0875.simplesethome.Waypoint;
import com.github.lapis0875.simplesethome.WaypointDataType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HomeCommandExecutor implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args
    ) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!command.getName().equals(Constants.CMD_WAYPOINT)){
                return true;
            }
            switch (command.getName()) {
                case Constants.CMD_SETHOME:
                    // sethome
                    return this.sethome(player);
                case Constants.CMD_HOME:
                    // home
                    return this.home(player);
                case Constants.CMD_BACK:
                    // back
                    return this.back(player);
            }
        }
        return true;
    }

    private boolean sethome(
            @NotNull Player player
    ) {
        Waypoint waypoint = new Waypoint(Constants.WAYPOINT, player.getLocation());
//        player.getPersistentDataContainer().set(wayPointKey, new WaypointDataType(), waypoint);
        player.getPersistentDataContainer().set(
                waypoint.getNamespacedKey(),
                new WaypointDataType(),
                waypoint
        );

        player.sendMessage(
            Component.text(
                String.format(
                    "(%s) %f, %f, %f에 경유지를 지정했습니다.",
                    waypoint.getWorld().getEnvironment().name(),
                    waypoint.x,
                    waypoint.y,
                    waypoint.z
                ),
            TextColor.color(171, 242, 0)
        ));
        return true;
    }

    private boolean home(@NotNull Player player) {
        Optional<Waypoint> waypoint = Optional.ofNullable(player.getPersistentDataContainer().get(
                        new NamespacedKey(
                                SimpleHome.instance(),
                                Constants.WAYPOINT
                        ),
                        new WaypointDataType()
        ));

        if (waypoint.isPresent()) {
            Waypoint w = waypoint.get();
            player.sendMessage(Component.text("저장된 위치로 이동합니다!", TextColor.color(171, 242, 0)));
            boolean result = player.teleport(new Location(w.getWorld(), w.x, w.y, w.z));
            if (!result) {
                player.sendMessage(Component.text("알 수 없는 오류로 이동하지 못했습니다 :(", TextColor.color(200, 0, 0)));
            }
        }
        else {
            player.sendMessage(Component.text("저장된 위치가 없습니다 :(", TextColor.color(200, 0, 0)));
        }
        return true;
    }

    private boolean back(@NotNull Player player) {
        NamespacedKey deathLocationKey = new NamespacedKey(SimpleHome.getPlugin(SimpleHome.class), Constants.DEATH_LOCATION);
        Optional<Waypoint> deathLocation = Optional.ofNullable(player.getPersistentDataContainer().get(deathLocationKey, new WaypointDataType()));
        if (deathLocation.isPresent()) {
            Waypoint deathPoint = deathLocation.get();
            player.sendMessage(Component.text("마지막 사망 위치로 이동합니다!", TextColor.color(171, 242, 0)));
            boolean result = player.teleport(new Location(deathPoint.getWorld(), deathPoint.x, deathPoint.y, deathPoint.z));
            if (result) {
                player.getPersistentDataContainer().remove(new NamespacedKey(SimpleHome.instance(), Constants.DEATH_LOCATION));
            }
            else {
                player.sendMessage(Component.text("알수 없는 오류로 이동에 실패했습니다 :(", TextColor.color(200, 0, 0)));
            }
        }
        else {
            player.sendMessage(Component.text("저장된 사망 위치가 없습니다!", TextColor.color(200, 0, 0)));
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> tabCompletion = new ArrayList<>();
        if (command.getName().equals(Constants.CMD_WAYPOINT)) {
            if (sender instanceof Player) {
                switch (args.length) {
                    case 1:
                        tabCompletion.add(Constants.CMD_DEATH_LOCATION);
                        tabCompletion.add(Constants.CMD_REGISTER);
                        tabCompletion.add(Constants.CMD_SHOW);
                        tabCompletion.add(Constants.CMD_CLEAR);
                        tabCompletion.add(Constants.CMD_TELEPORT);
                        return tabCompletion;
                    case 2:
                        switch (args[0]) {
                            case Constants.CMD_DEATH_LOCATION:
                                tabCompletion.add(Constants.CMD_TELEPORT);
                                tabCompletion.add(Constants.CMD_SHOW);
                                break;
                            case Constants.CMD_CLEAR:
                                tabCompletion.add(Constants.WAYPOINT);
                                tabCompletion.add(Constants.DEATH_LOCATION);
                                break;
                        }
                }
            }
        }
        return tabCompletion;
    }
}
