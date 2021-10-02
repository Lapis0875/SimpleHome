package com.github.lapis0875.simplesethome;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class WaypointDataType implements PersistentDataType<PersistentDataContainer, Waypoint> {
    /**
     * Returns the primitive data type of this tag.
     *
     * @return the class
     */
    @Override
    public @NotNull Class<PersistentDataContainer> getPrimitiveType() {
        return PersistentDataContainer.class;
    }

    /**
     * Returns the complex object type the primitive value resembles.
     *
     * @return the class type
     */
    @Override
    public @NotNull Class<Waypoint> getComplexType() {
        return Waypoint.class;
    }

    /**
     * Returns the primitive data that resembles the complex object passed to
     * this method.
     *
     * @param complex the complex object instance
     * @param context the context this operation is running in
     * @return the primitive value
     */
    public @NotNull PersistentDataContainer toPrimitive(@NotNull Waypoint complex, @NotNull PersistentDataAdapterContext context) {
        return complex.toNBTTag(context.newPersistentDataContainer());
    }

    /**
     * Creates a complex object based of the passed primitive value
     *
     * @param primitive the primitive value
     * @param context the context this operation is running in
     * @return the complex object instance
     */
    public @NotNull Waypoint fromPrimitive(@NotNull PersistentDataContainer primitive, @NotNull PersistentDataAdapterContext context) {
        return new Waypoint(
                primitive.get(new NamespacedKey(SimpleHome.instance(), "name"), PersistentDataType.STRING),
                primitive.get(new NamespacedKey(SimpleHome.instance(), "worldName"), PersistentDataType.STRING),
                primitive.get(new NamespacedKey(SimpleHome.instance(), "x"), PersistentDataType.DOUBLE),
                primitive.get(new NamespacedKey(SimpleHome.instance(), "y"), PersistentDataType.DOUBLE),
                primitive.get(new NamespacedKey(SimpleHome.instance(), "z"), PersistentDataType.DOUBLE)
        );
    }
}

