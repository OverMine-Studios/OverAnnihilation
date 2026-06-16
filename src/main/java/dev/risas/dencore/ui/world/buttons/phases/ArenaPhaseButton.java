package dev.risas.dencore.ui.world.buttons.phases;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.controllers.ConfigurationItemController;
import dev.risas.dencore.controllers.LobbyController;
import dev.risas.dencore.controllers.TeamController;
import dev.risas.dencore.controllers.WorldController;
import dev.risas.dencore.models.world.World;
import dev.risas.dencore.models.phase.Phase;
import dev.risas.dencore.ui.world.WorldPhaseMenu;
import dev.risas.dencore.utilities.TimeUtil;
import dev.risas.dencore.utilities.item.ItemBuilder;
import dev.risas.dencore.utilities.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * @author Risas
 * @date 20-05-2025
 * @discord https://risas.me/discord
 */
public class ArenaPhaseButton extends Button {

    private final DenCore plugin;
    private final Phase phase;
    private final World world;
    private final WorldController worldController;
    private final TeamController teamController;
    private final ConfigurationItemController configurationItemController;
    private final LobbyController lobbyController;

    public ArenaPhaseButton(
            DenCore plugin,
            Phase phase,
            World world,
            WorldController worldController,
            TeamController teamController,
            ConfigurationItemController configurationItemController,
            LobbyController lobbyController) {
        this.plugin = plugin;
        this.phase = phase;
        this.world = world;
        this.worldController = worldController;
        this.teamController = teamController;
        this.configurationItemController = configurationItemController;
        this.lobbyController = lobbyController;
    }

    @Override
    public ItemStack getItemStack(Player player) {
        return new ItemBuilder(Material.BEACON)
                .setDisplayName("&6Phase " + phase.getNumber())
                .setLore(
                        "",
                        "&7Duración: &f" + TimeUtil.toFormatDurationSeconds(phase.getDuration()),
                        "&7Diamantes: " + (phase.isDiamonds() ? "&aActivada" : "&cDesactivada"),
                        "&7Nexus: " + (phase.isNexus() ? "&aActivada" : "&cDesactivada"),
                        "&7Boss: " + (phase.isBoss() ? "&aActivada" : "&cDesactivada"),
                        "&7Witch: " + (phase.isWitch() ? "&aActivada" : "&cDesactivada"),
                        "&7Nexus Multiplier: " + (phase.isNexusMultiplier() ? "&aActivada" : "&cDesactivada"),
                        "",
                        "&eHaz clic para editar esta fase."
                )
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playNeutral(player);

        WorldPhaseMenu menu = new WorldPhaseMenu(
                player,
                plugin,
                phase,
                world,
                worldController,
                teamController,
                configurationItemController,
                lobbyController
        );
        menu.open();
    }

    @Override
    public boolean isCloseableAfterClick() {
        return false;
    }
}
