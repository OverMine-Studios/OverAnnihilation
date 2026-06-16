package dev.risas.dencore.utilities.location;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Risas
 * @date 10-06-2025
 * @discord https://risas.me/discord
 */
public class GameBlockLocation {

    private final String worldName;
    private final int x, y, z;

    public GameBlockLocation(
            String worldName,
            int x,
            int y,
            int z) {
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public GameBlockLocation(Location location) {
        this.worldName = location.getWorld().getName();
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    public Location getBukkitLocation() {
        World world = Bukkit.getWorld(worldName);
        if (world == null) return null;
        return new Location(world, x, y, z);
    }

    @Override
    public String toString() {
        return worldName + ";" + x + ";" + y + ";" + z;
    }

    public static GameBlockLocation fromString(String string) {
        if (string == null || string.isEmpty()) return null;

        String[] parts = string.split(";");

        if (parts.length != 4) {
            throw new IllegalArgumentException("Invalid GameBlockLocation string format: " + string);
        }

        return new GameBlockLocation(parts[0],
                Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2]),
                Integer.parseInt(parts[3]));
    }

    public static List<GameBlockLocation> fromStringList(List<String> strings) {
        if (strings == null || strings.isEmpty()) {
            return new ArrayList<>();
        }

        return strings.stream()
                .map(GameBlockLocation::fromString)
                .collect(Collectors.toList());
    }
}
