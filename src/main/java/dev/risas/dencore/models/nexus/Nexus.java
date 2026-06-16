package dev.risas.dencore.models.nexus;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

/**
 * @author Risas
 * @date 07-06-2025
 * @discord https://risas.me/discord
 */

@Getter @Setter
public class Nexus {

    private final Location location;
    private int health;
    private boolean multiplier;

    public Nexus(Location location) {
        this.location = location;
        this.health = 75;
    }

    public void addHealth(int health) {
        this.health += multiplier ? health * 2 : health;
    }

    public boolean isDestroyed() {
        return health <= 0;
    }

    public void damage(int amount) {
        this.health -= multiplier ? amount * 2 : amount;
    }
}
