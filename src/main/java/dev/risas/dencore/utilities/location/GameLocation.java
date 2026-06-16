package dev.risas.dencore.utilities.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * @author Risas
 * @date 10-06-2025
 * @discord https://risas.me/discord
 */
public class GameLocation {

    private final String worldName;
    private final double x, y, z;
    private final float yaw, pitch;

    public GameLocation(
            String worldName,
            double x,
            double y,
            double z,
            float yaw,
            float pitch) {
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public GameLocation(Location location) {
        this.worldName = location.getWorld().getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
    }

    public Location getBukkitLocation() {
        World world = Bukkit.getWorld(worldName);
        if (world == null) return null;
        return new Location(world, x, y, z, yaw, pitch);
    }

    @Override
    public String toString() {
        return worldName + ";" + x + ";" + y + ";" + z + ";" + yaw + ";" + pitch;
    }

    public static GameLocation fromString(String string) {
        if (string == null || string.isEmpty()) return null;

        String[] parts = string.split(";");

        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid GameLocation string format: " + string);
        }

        return new GameLocation(parts[0],
                Double.parseDouble(parts[1]),
                Double.parseDouble(parts[2]),
                Double.parseDouble(parts[3]),
                Float.parseFloat(parts[4]),
                Float.parseFloat(parts[5]));
    }
}
