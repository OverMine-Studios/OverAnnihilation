package dev.risas.dencore.ui.world.buttons.world;

import dev.risas.dencore.controllers.ConfigurationItemController;
import dev.risas.dencore.controllers.TeamController;
import dev.risas.dencore.models.configuration.types.WorldNexusLocationItem;
import dev.risas.dencore.models.world.World;
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
public class WorldNexusLocationButton extends Button {

    private final World world;
    private final TeamController teamController;
    private final ConfigurationItemController configurationItemController;

    public WorldNexusLocationButton(
            World world,
            TeamController teamController,
            ConfigurationItemController configurationItemController) {
        this.world = world;
        this.teamController = teamController;
        this.configurationItemController = configurationItemController;
    }

    @Override
    public ItemStack getItemStack(Player player) {
        return new ItemBuilder(Material.ENDER_STONE)
                .setDisplayName("&6World Nexus Team Location")
                .setLore("&7Marcar la ubicación de nexus de los equipos.")
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playNeutral(player);

        teamController.getTeams().values().forEach(team -> {
            WorldNexusLocationItem item = (WorldNexusLocationItem) configurationItemController.getConfigurationItemByClass(WorldNexusLocationItem.class);
            player.getInventory().addItem(item.getItemStack(world, team));
        });
    }
}
