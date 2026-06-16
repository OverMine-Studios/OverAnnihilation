package dev.risas.dencore.ui.world;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.controllers.ConfigurationItemController;
import dev.risas.dencore.controllers.LobbyController;
import dev.risas.dencore.controllers.TeamController;
import dev.risas.dencore.controllers.WorldController;
import dev.risas.dencore.models.world.World;
import dev.risas.dencore.models.phase.Phase;
import dev.risas.dencore.ui.world.buttons.phases.*;
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
public class WorldPhaseMenu extends Menu {

    private final DenCore plugin;
    private final Phase phase;
    private final World world;
    private final WorldController worldController;
    private final TeamController teamController;
    private final ConfigurationItemController configurationItemController;
    private final LobbyController lobbyController;

    public WorldPhaseMenu(
            Player player,
            DenCore plugin,
            Phase phase,
            World world,
            WorldController worldController,
            TeamController teamController,
            ConfigurationItemController configurationItemController,
            LobbyController lobbyController) {
        super(player, "Phase " + phase.getNumber(), 3);
        this.plugin = plugin;
        this.phase = phase;
        this.world = world;
        this.worldController = worldController;
        this.teamController = teamController;
        this.configurationItemController = configurationItemController;
        this.lobbyController = lobbyController;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        buttons.put(0, new ArenaPhaseDiamondButton(phase));
        buttons.put(1, new ArenaPhaseNexusButton(phase));
        buttons.put(2, new ArenaPhaseBossButton(phase));
        buttons.put(3, new ArenaPhaseWitchButton(phase));
        buttons.put(4, new ArenaPhaseNexusMultiplierButton(phase));

        buttons.put(22, new BackButton(new WorldPhasesMenu(player, plugin, world, worldController, teamController, configurationItemController, lobbyController)));
        buttons.put(23, new ArenaPhaseSaveButton(world, worldController));
        return buttons;
    }

    @Override
    public boolean isUpdateAfterClick() {
        return true;
    }
}
