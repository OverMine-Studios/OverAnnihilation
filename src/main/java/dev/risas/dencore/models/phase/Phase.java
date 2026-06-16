package dev.risas.dencore.models.phase;

import dev.risas.dencore.models.game.Game;
import dev.risas.dencore.models.world.World;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

/**
 * @author Risas
 * @date 19-05-2025
 * @discord https://risas.me/discord
 */

@Getter @Setter
public class Phase {

    private final PhaseType type;
    private int duration;
    private int remaining;

    private boolean diamonds, nexus, boss, witch, nexusMultiplier;

    public Phase(PhaseType type) {
        this.type = type;
        this.duration = 60;
        this.remaining = duration;
    }

    public Phase(PhaseType type, int duration, ConfigurationSection section) {
        this.type = type;
        this.duration = duration;
        this.remaining = duration;
        this.diamonds = section.getBoolean("diamonds");
        this.nexus = section.getBoolean("nexus");
        this.boss = section.getBoolean("boss");
        this.witch = section.getBoolean("witch");
        this.nexusMultiplier = section.getBoolean("nexus-multiplier");
    }

    public String getNumber() {
        return type.getRomanNumeral();
    }

    public boolean isFinished() {
        return this.remaining <= 0;
    }

    public void decreaseTimeRemaining() {
        this.remaining--;
    }

    public String getTimeRemaining() {
        return String.format("%02d:%02d", (remaining / 60), (remaining % 60));
    }

    public void onStart(World world, Game game) {
        if (diamonds) {
            world.applyDiamondBlock(Material.DIAMOND_ORE);
        }
        if (nexus) {
            world.applyNexusBlock(Material.ENDER_STONE);
        }
        if (boss) {
            // spawn bosses
        }
        if (witch) {
            // spawn witches
        }
        if (nexusMultiplier) {
            game.getTeams().forEach(team -> team.getNexus().setMultiplier(true));
        }
    }
}
