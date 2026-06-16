package dev.risas.dencore.models.game;

import dev.risas.dencore.controllers.*;
import dev.risas.dencore.models.nexus.Nexus;
import dev.risas.dencore.models.team.TeamPlayerChicken;
import dev.risas.dencore.models.world.World;
import dev.risas.dencore.models.kit.Kit;
import dev.risas.dencore.tasks.GameTask;
import dev.risas.dencore.models.phase.Phase;
import dev.risas.dencore.models.team.Team;
import dev.risas.dencore.models.user.User;
import dev.risas.dencore.utilities.FileConfig;
import dev.risas.dencore.utilities.PlayerUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

/**
 * @author Risas
 * @date 19-05-2025
 * @discord https://risas.me/discord
 */

@Getter @Setter
public class Game {

    private World world;
    private List<GameTeam> teams;
    private GameStatus status;
    private GameTask task;
    private Phase phase;
    private GameTeam winner;
    private boolean cancelled;

    private final Map<UUID, TeamPlayerChicken> chickens;

    public Game() {
        this.status = GameStatus.WAITING;
        this.chickens = new HashMap<>();
    }

    public void startGame(
            String worldName,
            WorldController worldController,
            TeamController teamController,
            GameController gameController) {
        World world = worldController.getWorld(worldName);
        Phase phase = world.getPhases().get(0);

        worldController.loadWorld(worldName);

        world.applyDiamondBlock(Material.COBBLESTONE);
        world.applyNexusBlock(Material.BEDROCK);

        this.world = world;
        this.phase = phase;

        List<GameTeam> gameTeams = new ArrayList<>();

        for (Team team : teamController.getTeams().values()) {
            Nexus nexus = new Nexus(world.getNexusLocation(team.getType()));
            GameTeam gameTeam = new GameTeam(team, nexus);

            for (Player player : teamController.getTeamPlayers(team)) {
                gameController.addGameTeam(player, team, gameTeam);
            }

            gameTeams.add(gameTeam);
        }

        this.teams = gameTeams;
        this.status = GameStatus.PLAYING;

        sendTitle(
                "&6&lPhase " + phase.getNumber(),
                "&fLa fase &e" + phase.getNumber() + " &fha comenzado.");
        sendMessage("&6[Phase] &fLa fase &e" + phase.getNumber() + " &fha comenzado.");
    }

    public void stopGame() {
        if (task != null) {
            task.cancel();
            task = null;
        }

        Bukkit.shutdown();
    }

    public int getRequiredPlayers(FileConfig configFile) {
        return configFile.getInt("game-system.required-players") - Bukkit.getOnlinePlayers().size();
    }

    public void onTeleportTeamSpawn(Player player, Team team) {
        player.teleport(world.getSpawnLocation(team.getType()));
    }

    public void applyTeamPlayerKit(Player player,
                                     User user,
                                     Team team,
                                     KitController kitController) {
        PlayerInventory inventory = player.getInventory();

        inventory.clear();
        inventory.setArmorContents(team.getArmor());
        inventory.setContents(team.getTools());

        Kit kit = user.getKit(kitController);
        kit.apply(player);

        if (kit.getExtraItems() != null) {
            System.out.printf("extra items: " + Arrays.toString(kit.getExtraItems()));
            for (ItemStack itemStack : kit.getExtraItems()) {
                PlayerUtil.replaceOrGiveItemStack(inventory, itemStack);
            }
        }
    }

    public void respawnPlayer(Player player, User user, Team team, KitController kitController) {
        applyTeamPlayerKit(player, user, team, kitController);
        onTeleportTeamSpawn(player, team);
    }

    public TeamPlayerChicken getChickenByEntity(Chicken chicken) {
        return chickens.values().stream()
                .filter(teamPlayerChicken -> teamPlayerChicken.getChicken().equals(chicken))
                .findFirst()
                .orElse(null);
    }

    public void addChicken(Player player, GameTeam gameTeam) {
        PlayerInventory inventory = player.getInventory();
        TeamPlayerChicken chicken = new TeamPlayerChicken(
                player,
                gameTeam,
                inventory.getArmorContents(),
                inventory.getContents()
        );
        chicken.spawn();

        chickens.put(player.getUniqueId(), chicken);
    }

    public void removeChicken(TeamPlayerChicken chicken) {
        chicken.despawn();
        chickens.remove(chicken.getUuid());
    }

    public void removeAllChickens() {
        for (TeamPlayerChicken chicken : chickens.values()) {
            chicken.despawn();
        }

        chickens.clear();
    }

    public void sendTitle(String title, String subtitle) {
        teams.forEach(matchTeam -> matchTeam.sendTitleMessage(title, subtitle));
    }

    public void sendMessage(String message) {
        teams.forEach(matchTeam -> matchTeam.sendMessage(message));
    }
}
