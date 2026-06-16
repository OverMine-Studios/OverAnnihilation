package dev.risas.dencore.models.game;

import dev.risas.dencore.controllers.GameController;
import dev.risas.dencore.models.nexus.Nexus;
import dev.risas.dencore.models.team.Team;
import dev.risas.dencore.models.team.TeamPlayer;
import dev.risas.dencore.models.team.TeamPlayerStatus;
import dev.risas.dencore.utilities.ChatUtil;
import dev.risas.dencore.utilities.PlayerUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Risas
 * @date 21-05-2025
 * @discord https://risas.me/discord
 */

@Getter @Setter
public class GameTeam {

    private final Nexus nexus;
    private final Team team;
    private final Map<UUID, TeamPlayer> players;

    public GameTeam(Team team, Nexus nexus) {
        this.team = team;
        this.nexus = nexus;
        this.players = new HashMap<>();
    }

    public boolean isAlive() {
        return players.values().stream().anyMatch(player ->
                player.getStatus() == TeamPlayerStatus.ALIVE ||
                player.getStatus() == TeamPlayerStatus.RESPAWNED);
    }

    public TeamPlayer getTeamPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public void addTeamPlayer(TeamPlayer teamPlayer) {
        players.put(teamPlayer.getUuid(), teamPlayer);
    }

    public void removeTeamPlayer(
            TeamPlayer teamPlayer,
            Player player,
            GameController gameController) {
        team.removeScoreboardTeam(teamPlayer, player);
        players.remove(teamPlayer.getUuid());
        gameController.removeGameTeam(player.getUniqueId());
    }

    public void sendTitleMessage(String title, String subtitle) {
        this.getOnlinePlayers().forEach(player -> PlayerUtil.sendTitle(player, title, subtitle));
    }

    public void sendMessage(String message) {
        this.getOnlinePlayers().forEach(player -> ChatUtil.sendMessage(player, message));
    }

    public void sendSound(Sound sound) {
        this.getOnlinePlayers().forEach(player -> PlayerUtil.sendSound(player, sound));
    }

    public Collection<Player> getOnlinePlayers() {
        return this.players.values().stream()
                .map(TeamPlayer::getPlayer)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
