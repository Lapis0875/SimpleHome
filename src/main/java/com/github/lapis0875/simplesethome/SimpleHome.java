package com.github.lapis0875.simplesethome;

import com.github.lapis0875.simplesethome.commands.HomeCommandExecutor;
import com.github.lapis0875.simplesethome.commands.WaypointsCommandExecutor;
import com.github.lapis0875.simplesethome.listeners.PlayerEventListener;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleHome extends JavaPlugin {

    public static SimpleHome instance() {
        return SimpleHome.getPlugin(SimpleHome.class);
    }
    @Override
    public void onEnable() {
        // Plugin startup logic
        CommandExecutor aliasExecutor = new HomeCommandExecutor();
        getCommand(Constants.CMD_WAYPOINT).setExecutor(new WaypointsCommandExecutor());
        getCommand(Constants.CMD_SETHOME).setExecutor(aliasExecutor);
        getCommand(Constants.CMD_HOME).setExecutor(aliasExecutor);
        getCommand(Constants.CMD_BACK).setExecutor(aliasExecutor);
        getServer().getPluginManager().registerEvents(new PlayerEventListener(), this);
        getLogger().info("Loaded.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Unloaded.");
    }
}
