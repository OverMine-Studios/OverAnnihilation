package dev.risas.dencore.models.world;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.models.phase.Phase;
import dev.risas.dencore.models.phase.PhaseType;
import dev.risas.dencore.models.team.TeamType;
import dev.risas.dencore.utilities.FileConfig;
import dev.risas.dencore.utilities.cuboid.Cuboid;
import dev.risas.dencore.utilities.location.GameBlockLocation;
import dev.risas.dencore.utilities.location.GameLocation;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Risas
 * @date 19-05-2025
 * @discord https://risas.me/discord
 */

@Getter @Setter
public class World {

    private final String name;
    private org.bukkit.World bukkitWorld;
    private FileConfig configFile;
    private Map<TeamType, WorldTeam> teams;
    private Map<Material, Long> ores;
    private List<GameBlockLocation> diamonds;
    private List<Phase> phases;
    private int phaseTime;

    public World(DenCore plugin, String worldName) {
        this.name = worldName;
        this.configFile = new FileConfig(plugin, "worlds/" + worldName + "/config.yml");
        this.teams = new HashMap<>();

        for (TeamType type : TeamType.values()) {
            this.teams.put(type, new WorldTeam(type));
        }

        this.ores = new HashMap<>();
        this.diamonds = new ArrayList<>();
        this.phases = new ArrayList<>();
        this.phases.add(new Phase(PhaseType.PHASE_I));
        this.phases.add(new Phase(PhaseType.PHASE_II));
        this.phases.add(new Phase(PhaseType.PHASE_III));
        this.phases.add(new Phase(PhaseType.PHASE_IV));
        this.phases.add(new Phase(PhaseType.PHASE_V));
        this.phases.add(new Phase(PhaseType.PHASE_VI));
        this.phases.add(new Phase(PhaseType.PHASE_VII));
        this.phaseTime = 60;
    }

    public World(String worldName, FileConfig configFile) {
        this.name = worldName;
        this.configFile = configFile;
        this.teams = new HashMap<>();

        ConfigurationSection section = configFile.getConfiguration();

        this.phaseTime = section.getInt("phase-time");

        ConfigurationSection teamsSection = section.getConfigurationSection("teams");

        if (teamsSection != null) {
            for (String teamId : teamsSection.getKeys(false)) {
                TeamType teamType = TeamType.valueOf(teamId);
                this.teams.put(teamType, new WorldTeam(teamType, teamsSection.getConfigurationSection(teamId)));
            }
        }

        this.ores = new HashMap<>();

        ConfigurationSection oresSection = section.getConfigurationSection("ores");

        if (oresSection != null) {
            for (String materialName : oresSection.getKeys(false)) {
                this.ores.put(Material.valueOf(materialName), oresSection.getLong(materialName));
            }
        }

        this.diamonds = GameBlockLocation.fromStringList(section.getStringList("diamonds"));
        this.phases = new ArrayList<>();

        ConfigurationSection phasesSection = section.getConfigurationSection("phases");

        if (phasesSection != null) {
            for (String phaseName : phasesSection.getKeys(false)) {
                this.phases.add(new Phase(PhaseType.valueOf(phaseName), phaseTime, phasesSection.getConfigurationSection(phaseName)));
            }
        }
    }

    public WorldTeam getWorldTeam(TeamType type) {
        return teams.get(type);
    }

    public Phase getNextPhase(Phase currentPhase) {
        int index = phases.indexOf(currentPhase);

        if (index == -1 || index + 1 >= phases.size()) {
            return null;
        }

        return phases.get(index + 1);
    }

    public void applyDiamondBlock(Material material) {
        diamonds.forEach(blockLocation -> {
            Location location = blockLocation.getBukkitLocation();

            Block block = location.getBlock();
            block.setType(material);
        });
    }

    public void applyNexusBlock(Material material) {
        for (WorldTeam worldTeam : teams.values()) {
            Location nexusLocation = worldTeam.getNexusLocation().getBukkitLocation();

            if (nexusLocation != null) {
                Block block = nexusLocation.getBlock();
                block.setType(material);
            }
        }
    }

    public void addDiamondLocation(Location location) {
        this.diamonds.add(new GameBlockLocation(location));
    }

    public void removeDiamondLocation(Location location) {
        this.diamonds.removeIf(blockLocation -> blockLocation.getBukkitLocation().equals(location));
    }

    public boolean isDiamondLocation(Location location) {
        return diamonds.stream()
                .anyMatch(blockLocation -> blockLocation.getBukkitLocation().equals(location));
    }

    public boolean isOre(Material material) {
        return ores.containsKey(material);
    }

    public long getOreTime(Material material) {
        return ores.get(material);
    }

    public void setSpawnLocation(TeamType type, Location location) {
        getWorldTeam(type).setSpawnLocation(new GameLocation(location));
    }

    public Location getSpawnLocation(TeamType teamType) {
        return teams.get(teamType).getSpawnLocation().getBukkitLocation();
    }

    public void setNexusLocation(TeamType type, Location location) {
        getWorldTeam(type).setNexusLocation(new GameBlockLocation(location));
    }

    public Location getNexusLocation(TeamType teamType) {
        return teams.get(teamType).getNexusLocation().getBukkitLocation();
    }

    public void setArea(TeamType type, Cuboid cuboid) {
        getWorldTeam(type).setArea(cuboid);
    }

    public Cuboid getArea(TeamType teamType) {
        return teams.get(teamType).getArea();
    }

    public boolean isProtectedArea(TeamType type, Location location) {
        WorldTeam team = getWorldTeam(type);

        if (team != null && team.getArea() != null) {
            return team.getArea().contains(location);
        }

        return false;
    }
}
