package dev.risas.dencore.controllers;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.models.game.GameStatus;
import dev.risas.dencore.models.nexus.Nexus;
import dev.risas.dencore.models.game.Game;
import dev.risas.dencore.models.game.GameTeam;
import dev.risas.dencore.models.team.Team;
import dev.risas.dencore.models.team.TeamPlayer;
import dev.risas.dencore.models.world.World;
import dev.risas.dencore.tasks.GameTask;
import dev.risas.dencore.utilities.FileConfig;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * @author Risas
 * @date 21-05-2025
 * @discord https://risas.me/discord
 */
public class GameController {

    @Getter private final Game game;
    private final Map<UUID, GameTeam> gameTeams;

    private final DenCore plugin;
    private final FileConfig configFile;
    private final TeamController teamController;
    private final UserController userController;
    private final KitController kitController;
    private final VoteController voteController;
    private final WorldController worldController;

    public GameController(
            DenCore plugin,
            FileConfig configFile,
            TeamController teamController,
            UserController userController,
            KitController kitController,
            VoteController voteController,
            WorldController worldController) {
        this.game = new Game();
        this.gameTeams = new HashMap<>();

        this.plugin = plugin;
        this.configFile = configFile;
        this.teamController = teamController;
        this.userController = userController;
        this.kitController = kitController;
        this.voteController = voteController;
        this.worldController = worldController;
    }

    public boolean canInteractInTeamArea(Player player, Location location) {
        if (game.getStatus() != GameStatus.PLAYING) return true;

        GameTeam gameTeam = getGameTeam(player.getUniqueId());
        if (gameTeam == null) return true;

        return !isTeamProtectedArea(game.getWorld(), location);
    }

    public boolean isTeamProtectedArea(World world, Location location) {
        return game.getTeams().stream().anyMatch(gameTeam -> world.isProtectedArea(gameTeam.getTeam().getType(), location));
    }

    public Nexus getNexusByLocation(Location location) {
        for (GameTeam gameTeam : gameTeams.values()) {
            Nexus nexus = gameTeam.getNexus();

            if (nexus.getLocation().equals(location)) {
                return nexus;
            }
        }
        return null;
    }

    public Nexus getNexusByTeam(Team team) {
        for (GameTeam gameTeam : gameTeams.values()) {
            System.out.printf("game team: " + gameTeam.getTeam().getName());
            Nexus nexus = gameTeam.getNexus();

            if (gameTeam.getTeam().equals(team)) {
                return nexus;
            }
        }
        return null;
    }

    public boolean isGameRunning() {
        return game.getStatus() != GameStatus.WAITING && game.getStatus() != GameStatus.STARTING;
    }

    public GameTeam getGameTeam(UUID uuid) {
        return gameTeams.get(uuid);
    }

    public GameTeam getGameTeamByNexus(Nexus nexus) {
        return game.getTeams().stream()
                .filter(gameTeam -> gameTeam.getNexus().equals(nexus))
                .findFirst()
                .orElse(null);
    }

    public GameTeam getGameTeamByTeam(Team team) {
        return game.getTeams().stream()
                .filter(gameTeam -> gameTeam.getTeam().equals(team))
                .findFirst()
                .orElse(null);
    }

    public TeamPlayer getTeamPlayer(UUID uuid) {
        return getGameTeam(uuid).getTeamPlayer(uuid);
    }

    public void addGameTeam(Player player, Team team, GameTeam gameTeam) {
        TeamPlayer teamPlayer = new TeamPlayer(player.getUniqueId(), player.getName(), team);
        team.addScoreboardTeam(teamPlayer, player);

        game.onTeleportTeamSpawn(player, team);
        game.applyTeamPlayerKit(
                player,
                userController.getUser(player.getUniqueId()),
                team,
                kitController
        );

        gameTeams.put(player.getUniqueId(), gameTeam);
        gameTeam.addTeamPlayer(teamPlayer);
    }

    public void addGameTeam(Player player, Team team) {
        GameTeam gameTeam = getGameTeamByTeam(team);
        addGameTeam(player, team, gameTeam);
    }

    public void removeGameTeam(UUID uuid) {
        gameTeams.remove(uuid);
    }

    public boolean hasTeam(UUID uuid) {
        return gameTeams.containsKey(uuid);
    }

    public void startGame(String worldName, int startingTime) {
        GameTask gameTask = new GameTask(
                worldName,
                startingTime,
                teamController,
                this,
                voteController,
                worldController
        );
        gameTask.start(plugin);

        this.game.setTask(gameTask);
        this.game.setStatus(GameStatus.STARTING);
    }

    public void startGame(String worldName) {
        startGame(worldName, configFile.getInt("game-system.starting-time"));
    }
}
