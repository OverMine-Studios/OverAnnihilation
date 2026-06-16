package dev.risas.dencore.ui.world;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.controllers.ConfigurationItemController;
import dev.risas.dencore.controllers.LobbyController;
import dev.risas.dencore.controllers.TeamController;
import dev.risas.dencore.controllers.WorldController;
import dev.risas.dencore.models.world.World;
import dev.risas.dencore.ui.world.buttons.main.WorldLoadWorldButton;
import dev.risas.dencore.ui.world.buttons.main.WorldLobbyButton;
import dev.risas.dencore.ui.world.buttons.main.WorldSetLobbyButton;
import dev.risas.dencore.ui.world.buttons.phases.ArenaPhasesButton;
import dev.risas.dencore.ui.world.buttons.world.*;
import dev.risas.dencore.utilities.menu.Button;
import dev.risas.dencore.utilities.menu.Menu;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Risas
 * @date 19-05-2025
 * @discord https://risas.me/discord
 */
public class WorldEditorMenu extends Menu {

    private final DenCore plugin;
    private final World world;
    private final WorldController worldController;
    private final TeamController teamController;
    private final ConfigurationItemController configurationItemController;
    private final LobbyController lobbyController;

    public WorldEditorMenu(
            Player player,
            DenCore plugin,
            World world,
            WorldController worldController,
            TeamController teamController,
            ConfigurationItemController configurationItemController,
            LobbyController lobbyController) {
        super(
                player,
                (world == null ? "World" : world.getName()) + " Editor",
                world == null ? 1 : 2
        );
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

        if (worldController.hasSelectedWorld()) {
            buttons.put(0, new WorldSpawnLocationButton(world, teamController, configurationItemController));
            buttons.put(1, new WorldNexusLocationButton(world, teamController, configurationItemController));
            buttons.put(2, new WorldAreaWandButton(world, teamController, configurationItemController));
            buttons.put(3, new WorldDiamondWandButton(world, configurationItemController));
            buttons.put(4, new WorldPhaseDurationButton(plugin, world, worldController, teamController, configurationItemController, lobbyController));
            buttons.put(5, new ArenaPhasesButton(plugin, world, worldController, teamController, configurationItemController, lobbyController));
            buttons.put(13, new WorldBackButton(worldController));
        }
        else {
            buttons.put(0, new WorldSetLobbyButton(lobbyController));
            buttons.put(1, new WorldLobbyButton(lobbyController));
            buttons.put(2, new WorldLoadWorldButton(worldController));
        }
        return buttons;
    }

    @Override
    public boolean isUpdateAfterClick() {
        return true;
    }
}
