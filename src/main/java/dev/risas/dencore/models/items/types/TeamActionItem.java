package dev.risas.dencore.models.items.types;

import dev.risas.dencore.controllers.GameController;
import dev.risas.dencore.controllers.TeamController;
import dev.risas.dencore.models.items.ActionItem;
import dev.risas.dencore.ui.team.TeamMenu;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class TeamActionItem extends ActionItem {

    private final TeamController teamController;
    private final GameController gameController;

    public TeamActionItem(ConfigurationSection section, TeamController teamController, GameController gameController) {
        super("team", section.getConfigurationSection("team"));
        this.teamController = teamController;
        this.gameController = gameController;
    }

    @Override
    public void onAction(Player player) {
        TeamMenu teamMenu = new TeamMenu(player, teamController, gameController);
        teamMenu.open();
    }
}
