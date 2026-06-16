package dev.risas.dencore.ui.world.buttons.world;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.controllers.ConfigurationItemController;
import dev.risas.dencore.controllers.LobbyController;
import dev.risas.dencore.controllers.TeamController;
import dev.risas.dencore.controllers.WorldController;
import dev.risas.dencore.models.world.World;
import dev.risas.dencore.models.world.prompts.WorldPhaseDurationPrompt;
import dev.risas.dencore.utilities.PromptUtil;
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
public class WorldPhaseDurationButton extends Button {

    private final DenCore plugin;
    private final World world;
    private final WorldController worldController;
    private final TeamController teamController;
    private final ConfigurationItemController configurationItemController;
    private final LobbyController lobbyController;

    public WorldPhaseDurationButton(
            DenCore plugin,
            World world,
            WorldController worldController,
            TeamController teamController,
            ConfigurationItemController configurationItemController,
            LobbyController lobbyController) {
        this.plugin = plugin;
        this.world = world;
        this.worldController = worldController;
        this.teamController = teamController;
        this.configurationItemController = configurationItemController;
        this.lobbyController = lobbyController;
    }

    @Override
    public ItemStack getItemStack(Player player) {
        return new ItemBuilder(Material.WATCH)
                .setDisplayName("&6World Phase Duration")
                .setLore("&7Establecer la duración de las fases del mapa.")
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playNeutral(player);
        PromptUtil.createPrompt(player, new WorldPhaseDurationPrompt(
                plugin,
                world,
                worldController,
                teamController,
                configurationItemController,
                lobbyController
        ));
    }
}
