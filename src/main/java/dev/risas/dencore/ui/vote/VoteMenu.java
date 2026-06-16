package dev.risas.dencore.ui.vote;

import dev.risas.dencore.controllers.GameController;
import dev.risas.dencore.controllers.VoteController;
import dev.risas.dencore.controllers.WorldController;
import dev.risas.dencore.ui.vote.buttons.VoteButton;
import dev.risas.dencore.utilities.menu.Button;
import dev.risas.dencore.utilities.menu.Menu;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Risas
 * @date 08-06-2025
 * @discord https://risas.me/discord
 */
public class VoteMenu extends Menu {

    private final VoteController voteController;
    private final WorldController worldController;
    private final GameController gameController;

    public VoteMenu(
            Player player,
            VoteController voteController,
            WorldController worldController,
            GameController gameController) {
        super(player, "Vota por un mapa", 1);
        this.voteController = voteController;
        this.worldController = worldController;
        this.gameController = gameController;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        worldController.getWorlds()
                .forEach(world -> buttons.put(buttons.size(), new VoteButton(world, voteController, gameController)));

        return buttons;
    }
}
