package dev.risas.dencore.models.configuration.types;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.controllers.ConfigurationItemController;
import dev.risas.dencore.controllers.LobbyController;
import dev.risas.dencore.controllers.TeamController;
import dev.risas.dencore.controllers.WorldController;
import dev.risas.dencore.models.world.World;
import dev.risas.dencore.models.configuration.ConfigurationItem;
import dev.risas.dencore.ui.world.WorldEditorMenu;
import dev.risas.dencore.utilities.item.ItemBuilder;
import dev.risas.dencore.utilities.item.nbt.NBTBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

/**
 * @author Risas
 * @date 19-05-2025
 * @discord https://risas.me/discord
 */

public class WorldEditorItem extends ConfigurationItem {

    private final DenCore plugin;
    private final WorldController worldController;
    private final TeamController teamController;
    private final ConfigurationItemController configurationItemController;
    private final LobbyController lobbyController;

    public WorldEditorItem(
            String id,
            DenCore plugin,
            WorldController worldController,
            TeamController teamController,
            ConfigurationItemController configurationItemController,
            LobbyController lobbyController) {
        super(id, new NBTBuilder(new ItemBuilder(Material.DIAMOND_PICKAXE)
                .setDisplayName("&6World Editor")
                .setLore("&7Clic derecho para abrir el menu")
                .build())
                .setSoulbound(true)
                .setConfigurationItem(id)
                .build());
        this.plugin = plugin;
        this.worldController = worldController;
        this.teamController = teamController;
        this.configurationItemController = configurationItemController;
        this.lobbyController = lobbyController;
    }

    @Override
    public void call(Player player, Action action, ItemStack itemStack) {
        if (isLeftAction(action)) return;

        World world = worldController.getSelectedWorld();

        WorldEditorMenu menu = new WorldEditorMenu(
                player,
                plugin,
                world,
                worldController,
                teamController,
                configurationItemController,
                lobbyController
        );
        menu.open();
    }
}
