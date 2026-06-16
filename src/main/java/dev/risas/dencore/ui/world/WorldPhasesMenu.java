package dev.risas.dencore.ui.world;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.controllers.ConfigurationItemController;
import dev.risas.dencore.controllers.LobbyController;
import dev.risas.dencore.controllers.TeamController;
import dev.risas.dencore.controllers.WorldController;
import dev.risas.dencore.models.world.World;
import dev.risas.dencore.ui.world.buttons.phases.ArenaPhaseButton;
import dev.risas.dencore.utilities.menu.Button;
import dev.risas.dencore.utilities.menu.Menu;
import dev.risas.dencore.utilities.menu.buttons.BackButton;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Risas
 * @date 21-05-2025
 * @discord https://risas.me/discord
 */
public class WorldPhasesMenu extends Menu {

    private final DenCore plugin;
    private final World world;
    private final WorldController worldController;
    private final TeamController teamController;
    private final ConfigurationItemController configurationItemController;
    private final LobbyController lobbyController;

    public WorldPhasesMenu(
            Player player,
            DenCore plugin,
            World world,
            WorldController worldController,
            TeamController teamController,
            ConfigurationItemController configurationItemController,
            LobbyController lobbyController) {
        super(player, "Phases Menu", 1);
        this.plugin = plugin;
        this.world = world;
        this.worldController = worldController;
        this.teamController = teamController;
        this.configurationItemController = configurationItemController;
        this.lobbyController = lobbyController;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        world.getPhases().forEach(phase ->
                buttons.put(buttons.size(), new ArenaPhaseButton(plugin, phase, world, worldController, teamController, configurationItemController, lobbyController)));

        buttons.put(8, new BackButton(new WorldEditorMenu(player, plugin, world, worldController, teamController, configurationItemController, lobbyController)));
        return buttons;
    }
}
