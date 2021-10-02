package com.github.lapis0875.simplesethome;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.Serializable;

public class Waypoint implements Serializable {
    public final String name;
    public final double x;
    public final double y;
    public final double z;
    public final String worldName;

    public Waypoint(String name, Location location) {
        this.name = name;
        this.worldName = location.getWorld().getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
    }

    public Waypoint(String name, String worldName, double x, double y, double z) {
        this.name = name;
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public World getWorld() {
        return Bukkit.getWorld(this.worldName);
    }

    public PersistentDataContainer toNBTTag(PersistentDataContainer tag) {
        tag.set(new NamespacedKey(SimpleHome.getPlugin(SimpleHome.class), "x"), PersistentDataType.DOUBLE, this.x);
        tag.set(new NamespacedKey(SimpleHome.getPlugin(SimpleHome.class), "y"), PersistentDataType.DOUBLE, this.y);
        tag.set(new NamespacedKey(SimpleHome.getPlugin(SimpleHome.class), "z"), PersistentDataType.DOUBLE, this.z);
        tag.set(new NamespacedKey(SimpleHome.getPlugin(SimpleHome.class), "name"), PersistentDataType.STRING, this.name);
        tag.set(new NamespacedKey(SimpleHome.getPlugin(SimpleHome.class), "worldName"), PersistentDataType.STRING, this.worldName);
        return tag;
    }

    public NamespacedKey getNamespacedKey() {
        return new NamespacedKey(SimpleHome.instance(), this.name);
    }
}
