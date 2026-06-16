package dev.risas.dencore.ui.world;

import dev.risas.dencore.controllers.WorldController;
import dev.risas.dencore.ui.world.buttons.main.WorldButton;
import dev.risas.dencore.utilities.menu.Button;
import dev.risas.dencore.utilities.menu.Menu;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Risas
 * @date 20-05-2025
 * @discord https://risas.me/discord
 */
public class WorldMenu extends Menu {

    private final WorldController worldController;

    public WorldMenu(Player player, WorldController worldController) {
        super(
                player,
                "Mapas",
                6
        );
        this.worldController = worldController;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        worldController.getWorlds().forEach(world ->
                buttons.put(buttons.size(), new WorldButton(world, worldController)));

        return buttons;
    }
}
