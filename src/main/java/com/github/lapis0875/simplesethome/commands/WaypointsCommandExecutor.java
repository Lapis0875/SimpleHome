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

public class WaypointsCommandExecutor implements CommandExecutor, TabCompleter {
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
            switch (args[0]) {
                case Constants.CMD_DEATH_LOCATION:
                case "deathpoint":
                case "dl":
                case "dp":
                case "death":
                    // Death Location Command.
                    if (args.length < 2){
                        player.sendMessage(Component.text(
                                "명령어 인자가 부족합니다!",
                                TextColor.color(200, 0, 0)
                        ));
                        return true;
                    }
                    return this.onDeathLocationCommand(player, args[1]);
                case Constants.CMD_REGISTER:
                case "set":
                    // Death Location Command.
                    if (args.length != 1){
                        player.sendMessage(Component.text(
                                "명령어 인자가 잘못되었습니다!",
                                TextColor.color(200, 0, 0)
                        ));
                        return true;
                    }
                    return this.onWaypointRegister(player);
                case Constants.CMD_TELEPORT:
                case "move":
                case "go":
                case "tp":
                    // Death Location Command.
                    if (args.length != 1){
                        player.sendMessage(Component.text(
                                "명령어 인자가 잘못되었습니다!",
                                TextColor.color(200, 0, 0)
                        ));
                        return true;
                    }
                    return this.onWaypointTeleport(player);
                case "list":
                case Constants.CMD_SHOW:
                    // Death Location Command.
                    if (args.length != 1){
                        player.sendMessage(Component.text(
                                "명령어 인자가 잘못되었습니다!",
                                TextColor.color(200, 0, 0)
                        ));
                        return true;
                    }
                    return this.onWaypointShow(player);
                case Constants.CMD_CLEAR:
                    // Death Location Command.
                    if (args.length != 2){
                        player.sendMessage(Component.text(
                                "명령어 인자가 잘못되었습니다!",
                                TextColor.color(200, 0, 0)
                        ));
                        return true;
                    }
                    return this.onWaypointRemove(player, args[1]);
                default:
                    return true;
            }
        }
        return true;
    }

    /*
    * Waypoint Commands
    */

    private boolean onWaypointRegister(
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

    private boolean onWaypointTeleport(@NotNull Player player) {
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

    private boolean onWaypointRemove(@NotNull Player player, @NotNull String type) {
        PersistentDataContainer playerNBT = player.getPersistentDataContainer();
        Optional<Waypoint> waypoint;
        switch (type) {
            case Constants.WAYPOINT:
                waypoint = Optional.ofNullable(playerNBT.get(
                        new NamespacedKey(
                                SimpleHome.instance(),
                                Constants.WAYPOINT
                        ),
                        new WaypointDataType()
                ));
                if (waypoint.isPresent()) {
                    playerNBT.remove(waypoint.get().getNamespacedKey());
                    player.sendMessage(Component.text("저장된 위치를 삭제했습니다.", TextColor.color(171, 242, 0)));
                }
                else {
                    player.sendMessage(Component.text("저장된 위치가 없습니다.", TextColor.color(200, 0, 0)));
                }
            case Constants.DEATH_LOCATION:
                waypoint = Optional.ofNullable(playerNBT.get(
                        new NamespacedKey(
                                SimpleHome.instance(),
                                Constants.DEATH_LOCATION
                        ),
                        new WaypointDataType()
                ));
                if (waypoint.isPresent()) {
                    playerNBT.remove(waypoint.get().getNamespacedKey());
                    player.sendMessage(Component.text("저장된 사망 위치를 삭제했습니다.", TextColor.color(171, 242, 0)));
                }
                else {
                    player.sendMessage(Component.text("저장된 사망 위치가 없습니다.", TextColor.color(200, 0, 0)));
                }
            default:
                player.sendMessage(Component.text(
                        String.format(
                            "%s는 잘못된 유형입니다. waypoint 또는 deathlocation을 입력하세요.",
                            type
                        ),
                        TextColor.color(200, 0, 0)
                ));
        }
        return true;
    }

    private boolean onWaypointShow(
            @NotNull Player player
    ) {
        NamespacedKey wayPointKey = new NamespacedKey(SimpleHome.instance(), Constants.WAYPOINT);
        Optional<Waypoint> waypoint = Optional.ofNullable(player.getPersistentDataContainer().get(wayPointKey, new WaypointDataType()));
        waypoint.ifPresent((Waypoint w) -> player.sendMessage(
            Component.text(
                String.format(
                    "집 (%s) : (%s) %f, %f, %f",
                    w.name,
                    w.getWorld().getEnvironment().name(),
                    w.x,
                    w.y,
                    w.z
                ),
                TextColor.color(171, 242, 0)
            )
        ));

        NamespacedKey deathLocationKey = new NamespacedKey(SimpleHome.instance(), Constants.DEATH_LOCATION);
        Optional<Waypoint> deathLocation = Optional.ofNullable(player.getPersistentDataContainer().get(deathLocationKey, new WaypointDataType()));
        deathLocation.ifPresent((Waypoint w) -> player.sendMessage(
            Component.text(
                String.format(
                    "사망 위치 : (%s) %f, %f, %f",
                    w.getWorld().getEnvironment().name(),
                    w.x,
                    w.y,
                    w.z
                ),
                TextColor.color(171, 242, 0)
            )
        ));
        return true;
    }

    /*
    * Death Location Commands
    */

    private boolean onDeathLocationCommand(
            @NotNull Player player,
            @NotNull String subcommand
    ) {
        NamespacedKey deathLocationKey = new NamespacedKey(SimpleHome.getPlugin(SimpleHome.class), Constants.DEATH_LOCATION);
        Optional<Waypoint> deathLocation = Optional.ofNullable(player.getPersistentDataContainer().get(deathLocationKey, new WaypointDataType()));

        if (!deathLocation.isPresent()) {
            player.sendMessage(Component.text("사망 위치가 저장되어있지 않습니다.", TextColor.color(200, 0, 0)));
            return true;
        }

        switch (subcommand) {
            case Constants.CMD_TELEPORT:
            case "tp":
            case "move":
            case "go":
                // Teleport player to death location
                return this.teleportToDeathLocation(player, deathLocation.get());
            case "list":
            case Constants.CMD_SHOW:
                // Show information of death location
                return this.showDeathLocation(player, deathLocation.get());
            default:
                player.sendMessage(Component.text("존재하지 않는 명령어입니다.", TextColor.color(200, 0, 0)));
                return true;
        }
    }

    private boolean teleportToDeathLocation(@NotNull Player player, @NotNull Waypoint deathPoint) {
        player.sendMessage(Component.text("마지막 사망 위치로 이동합니다!", TextColor.color(171, 242, 0)));
        boolean result = player.teleport(new Location(deathPoint.getWorld(), deathPoint.x, deathPoint.y, deathPoint.z));
        if (result) {
            player.getPersistentDataContainer().remove(new NamespacedKey(SimpleHome.instance(), Constants.DEATH_LOCATION));
        }
        else {
            player.sendMessage(Component.text("알수 없는 오류로 이동에 실패했습니다 :(", TextColor.color(200, 0, 0)));
        }
        return true;
    }

    private boolean showDeathLocation(@NotNull Player player, @NotNull Waypoint deathPoint) {
        player.sendMessage(
            Component.text(
                String.format(
                "당신이 마지막으로 죽은 장소는 (%s) {%f}, {%f}, {%f} 입니다.",
                    deathPoint.getWorld().getEnvironment().name(),
                    deathPoint.x,
                    deathPoint.y,
                    deathPoint.z
                ),
                TextColor.color(171, 242, 0)
            )
        );
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
