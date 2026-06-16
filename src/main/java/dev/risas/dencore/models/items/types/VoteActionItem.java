package dev.risas.dencore.models.items.types;

import dev.risas.dencore.controllers.GameController;
import dev.risas.dencore.controllers.VoteController;
import dev.risas.dencore.controllers.WorldController;
import dev.risas.dencore.models.items.ActionItem;
import dev.risas.dencore.ui.vote.VoteMenu;
import dev.risas.dencore.utilities.ChatUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class VoteActionItem extends ActionItem {

    private final VoteController voteController;
    private final WorldController worldController;
    private final GameController gameController;

    public VoteActionItem(
            ConfigurationSection section,
            VoteController voteController,
            WorldController worldController,
            GameController gameController) {
        super("vote", section.getConfigurationSection("vote"));
        this.voteController = voteController;
        this.worldController = worldController;
        this.gameController = gameController;
    }

    @Override
    public void onAction(Player player) {
        if (gameController.isGameRunning()) {
            ChatUtil.sendMessage(player, "&cYa se seleccionó un mapa, no puedes votar ahora.");
            return;
        }

        VoteMenu menu = new VoteMenu(player, voteController, worldController, gameController);
        menu.open();
    }
}
