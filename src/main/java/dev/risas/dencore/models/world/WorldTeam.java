package dev.risas.dencore.models.world;

import dev.risas.dencore.models.team.TeamType;
import dev.risas.dencore.utilities.cuboid.Cuboid;
import dev.risas.dencore.utilities.location.GameBlockLocation;
import dev.risas.dencore.utilities.location.GameLocation;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;

/**
 * @author Risas
 * @date 07-06-2025
 * @discord https://risas.me/discord
 */

@Getter @Setter
public class WorldTeam {

    private final TeamType type;
    private GameLocation spawnLocation;
    private GameBlockLocation nexusLocation;
    private Cuboid area;

    public WorldTeam(TeamType type) {
        this.type = type;
    }

    public WorldTeam(TeamType type, ConfigurationSection section) {
        this.type = type;
        this.spawnLocation = GameLocation.fromString(section.getString("spawn"));
        this.nexusLocation = GameBlockLocation.fromString(section.getString("nexus"));
        this.area = Cuboid.fromString(section.getString("area"));
    }
}
