package dev.risas.dencore.controllers;

import dev.risas.dencore.models.team.Team;
import dev.risas.dencore.models.team.TeamType;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
public class TeamController {

    private final Map<TeamType, Team> teams;
    private final Map<Team, List<Player>> teamSelection;

    public TeamController() {
        this.teams = new LinkedHashMap<>();
        this.teams.put(TeamType.RED, new Team(TeamType.RED, ChatColor.RED, Color.RED, "Rojo", 14));
        this.teams.put(TeamType.GREEN, new Team(TeamType.GREEN, ChatColor.GREEN, Color.GREEN, "Verde", 13));
        this.teams.put(TeamType.BLUE, new Team(TeamType.BLUE, ChatColor.BLUE, Color.BLUE, "Azul", 11));
        this.teams.put(TeamType.YELLOW, new Team(TeamType.YELLOW, ChatColor.YELLOW, Color.YELLOW, "Amarillo", 4));
        this.teamSelection = new HashMap<>();
    }

    public Team getTeam(TeamType teamType) {
        return teams.get(teamType);
    }

    public Team getTeamSelectionByPlayer(Player player) {
        return teamSelection.entrySet().stream()
                .filter(entry -> entry.getValue().contains(player))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    public List<Player> getTeamPlayers(Team team) {
        return teamSelection.getOrDefault(team, Collections.emptyList());
    }

    public boolean hasTeamSelection(Player player) {
        return teamSelection.values().stream()
                .anyMatch(players -> players.contains(player));
    }

    public void addTeamSelection(Team team, Player player) {
        teamSelection.computeIfAbsent(team, k -> new ArrayList<>()).add(player);
    }

    public void removeTeamSelection(Team team, Player player) {
        List<Player> players = teamSelection.get(team);

        if (players != null) {
            players.remove(player);

            if (players.isEmpty()) {
                teamSelection.remove(team);
            }
        }
    }
}
