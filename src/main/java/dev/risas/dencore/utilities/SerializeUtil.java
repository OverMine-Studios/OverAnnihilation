package dev.risas.dencore.utilities;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

@UtilityClass
public class SerializeUtil {

    public String serializeLocation(Location location) {
        if (location == null) return null;

        World world = location.getWorld();
        if (world == null) return null;

        return world.getName() + ";" +
                location.getX() + ";" +
                location.getY() + ";" +
                location.getZ() + ";" +
                location.getYaw() + ";" +
                location.getPitch();
    }

    public Location deserializeLocation(String location) {
        if (location == null || location.isEmpty()) return null;

        String[] parts = location.split(";");
        if (parts.length != 6) return null;

        World world = Bukkit.getWorld(parts[0]);
        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double z = Double.parseDouble(parts[3]);
        float yaw = Float.parseFloat(parts[4]);
        float pitch = Float.parseFloat(parts[5]);

        return new Location(world, x, y, z, yaw, pitch);
    }
}
