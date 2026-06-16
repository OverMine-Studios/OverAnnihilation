package dev.risas.dencore.ui.team;

import dev.risas.dencore.controllers.GameController;
import dev.risas.dencore.controllers.TeamController;
import dev.risas.dencore.models.team.Team;
import dev.risas.dencore.ui.team.buttons.TeamButton;
import dev.risas.dencore.ui.team.buttons.TeamResetButton;
import dev.risas.dencore.utilities.menu.Button;
import dev.risas.dencore.utilities.menu.Menu;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class TeamMenu extends Menu {

    private final TeamController teamController;
    private final GameController gameController;

    public TeamMenu(Player player, TeamController teamController, GameController gameController) {
        super(player, "Selecciona un equipo", 1);
        this.teamController = teamController;
        this.gameController = gameController;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        for (Team team : teamController.getTeams().values()) {
            buttons.put(buttons.size(), new TeamButton(team, teamController, gameController));
        }

        buttons.put(8, new TeamResetButton(teamController));
        return buttons;
    }
}
