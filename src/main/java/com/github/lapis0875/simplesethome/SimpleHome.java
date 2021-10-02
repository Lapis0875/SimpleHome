package com.github.lapis0875.simplesethome;

import com.github.lapis0875.simplesethome.commands.SetHomeCommandsExecutor;
import com.github.lapis0875.simplesethome.listeners.PlayerEventListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleHome extends JavaPlugin {

    public static SimpleHome instance() {
        return SimpleHome.getPlugin(SimpleHome.class);
    }
    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand(Constants.WAYPOINT_CMD).setExecutor(new SetHomeCommandsExecutor());
        getServer().getPluginManager().registerEvents(new PlayerEventListener(), this);
        getSLF4JLogger().info("Loaded.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getSLF4JLogger().info("Unloaded.");
    }
}
