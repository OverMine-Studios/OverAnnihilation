package dev.risas.dencore.controllers;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.models.phase.Phase;
import dev.risas.dencore.models.team.TeamType;
import dev.risas.dencore.models.world.World;
import dev.risas.dencore.models.world.WorldTeam;
import dev.risas.dencore.utilities.FileConfig;
import dev.risas.dencore.utilities.location.GameBlockLocation;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Risas
 * @date 07-06-2025
 * @discord https://risas.me/discord
 */
public class WorldController {

    private final DenCore plugin;
    private final Map<String, World> worlds;

    @Getter @Setter
    private World selectedWorld;

    public WorldController(DenCore plugin) {
        this.plugin = plugin;
        this.worlds = new HashMap<>();
        this.loadWorlds(plugin.getDataFolder());
    }

    public Collection<World> getWorlds() {
        return worlds.values();
    }

    public World getWorld(String worldName) {
        return worlds.get(worldName);
    }

    public boolean hasSelectedWorld() {
        return selectedWorld != null;
    }

    private void copyDirectory(Path source, Path target) throws IOException {
        Files.walk(source).forEach(path -> {
            try {
                Path destination = target.resolve(source.relativize(path));

                if (Files.isDirectory(path)) {
                    if (!Files.exists(destination)) {
                        Files.createDirectory(destination);
                    }
                }
                else {
                    Files.copy(path, destination);
                }
            } catch (IOException e) {
                throw new RuntimeException("Error al copiar archivos: " + e.getMessage(), e);
            }
        });
    }

    public void saveWorld(World world) {
        FileConfig configFile = world.getConfigFile();
        ConfigurationSection section = configFile.getConfiguration();

        section.set("name", world.getName());
        section.set("phase-time", world.getPhaseTime());

        if (world.getDiamonds() != null && !world.getDiamonds().isEmpty()) {
            section.set("diamonds", world.getDiamonds().stream()
                    .map(GameBlockLocation::toString)
                    .collect(Collectors.toList()));
        }

        for (Map.Entry<Material, Long> entry : world.getOres().entrySet()) {
            section.set("ores." + entry.getKey().name(), entry.getValue());
        }

        for (Map.Entry<TeamType, WorldTeam> entry : world.getTeams().entrySet()) {
            WorldTeam team = entry.getValue();
            String teamName = entry.getKey().name();

            if (team.getNexusLocation() != null) {
                section.set("teams." + teamName + ".nexus", team.getNexusLocation().toString());
            }
            if (team.getSpawnLocation() != null) {
                section.set("teams." + teamName + ".spawn", team.getSpawnLocation().toString());
            }
            if (team.getArea() != null) {
                section.set("teams." + teamName + ".area", team.getArea().toString());
            }
        }

        for (Phase phase : world.getPhases()) {
            String phaseName = phase.getType().name();
            section.set("phases." + phaseName + ".diamonds", phase.isDiamonds());
            section.set("phases." + phaseName + ".nexus", phase.isNexus());
            section.set("phases." + phaseName + ".boss", phase.isBoss());
            section.set("phases." + phaseName + ".witch", phase.isWitch());
            section.set("phases." + phaseName + ".nexus-multiplier", phase.isNexusMultiplier());
        }

        section.set("phase-time", world.getPhaseTime());

        configFile.save();
    }

    public org.bukkit.World loadWorld(String worldName) {
        File sourceWorld = new File(plugin.getDataFolder(), "worlds/" + worldName);
        File serverWorldsFolder = plugin.getServer().getWorldContainer();
        File destWorld = new File(serverWorldsFolder, worldName);

        try {
            if (destWorld.exists()) FileUtils.deleteDirectory(destWorld);
            FileUtils.copyDirectory(sourceWorld, destWorld);

            org.bukkit.World world = new WorldCreator(worldName)
                    .generateStructures(false)
                    .createWorld();

            if (world != null) {
                plugin.getLogger().info("Mundo '" + worldName + "' cargado correctamente.");
            }
            else {
                plugin.getLogger().warning("No se pudo cargar el mundo '" + worldName + "'.");
            }

            return world;
        }
        catch (IOException e) {
            plugin.getLogger().severe("Error copiando el mundo: " + e.getMessage());
            return null;
        }
    }

    public boolean unloadWorld(String worldName) {
        org.bukkit.World world = Bukkit.getWorld(worldName);

        if (world == null) {
            plugin.getLogger().warning("No se encontró el mundo '" + worldName + "' para descargar.");
            return false;
        }

        for (Player player : world.getPlayers()) {
            player.teleport(plugin.getLobbyController().getLocation(player));
        }

        boolean success = Bukkit.unloadWorld(world, false);

        if (success) {
            File serverWorldsFolder = plugin.getServer().getWorldContainer();
            File destWorld = new File(serverWorldsFolder, worldName);

            try {
                FileUtils.deleteDirectory(destWorld);
            }
            catch (IOException e) {
                plugin.getLogger().severe(e.getMessage());
            }

            plugin.getLogger().info("El mundo '" + worldName + "' se descargó correctamente.");
        }
        else {
            plugin.getLogger().severe("No se pudo descargar el mundo '" + worldName + "'.");
        }

        return success;
    }

    public void loadWorlds(File pluginFolder) {
        File worldsFolder = new File(pluginFolder, "worlds");
        File backupFolder = new File(pluginFolder, "worlds-backup");

        if (!worldsFolder.exists()) {
            if (worldsFolder.mkdirs()) {
                plugin.getLogger().info("Se creó la carpeta 'worlds' porque no existía.");
            }
            else {
                plugin.getLogger().severe("No se pudo crear la carpeta 'worlds'.");
                return;
            }
        }

        if (!backupFolder.exists()) {
            if (backupFolder.mkdirs()) {
                plugin.getLogger().info("Se creó la carpeta 'worlds-backup' porque no existía.");
            }
            else {
                plugin.getLogger().severe("No se pudo crear la carpeta 'worlds-backup'.");
                return;
            }
        }

        File[] worldDirs = worldsFolder.listFiles(File::isDirectory);

        if (worldDirs == null || worldDirs.length == 0) {
            plugin.getLogger().info("No se encontraron mundos en la carpeta 'worlds'.");
            return;
        }

        for (File worldFile : worldDirs) {
            String worldName = worldFile.getName();
            File backupWorld = new File(backupFolder, worldName);

            if (backupWorld.exists()) {
                plugin.getLogger().info("Ya existe un backup para el mundo: " + worldName);
                this.worlds.put(worldName, new World(worldName, new FileConfig(plugin, "worlds/" + worldName + "/config.yml")));
                continue;
            }

            try {
                copyDirectory(worldFile.toPath(), backupWorld.toPath());
                plugin.getLogger().info("Backup creado para el mundo: " + worldName);

                World world = new World(plugin, worldName);

                this.worlds.put(worldName, world);
                this.saveWorld(world);
            }
            catch (IOException e) {
                plugin.getLogger().severe("Error al crear el backup de " + worldFile.getName() + ": " + e.getMessage());
            }
        }
    }
}
