package dev.risas.dencore.ui.world.buttons.world;

import dev.risas.dencore.controllers.ConfigurationItemController;
import dev.risas.dencore.models.world.World;
import dev.risas.dencore.models.configuration.types.WorldDiamondWandItem;
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
public class WorldDiamondWandButton extends Button {

    private final World world;
    private final ConfigurationItemController configurationItemController;

    public WorldDiamondWandButton(
            World world,
            ConfigurationItemController configurationItemController) {
        this.world = world;
        this.configurationItemController = configurationItemController;
    }

    @Override
    public ItemStack getItemStack(Player player) {
        return new ItemBuilder(Material.DIAMOND)
                .setDisplayName("&6World Diamond Wand")
                .setLore("&7Marca las ubicaciones de los diamantes.")
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playNeutral(player);

        WorldDiamondWandItem item = (WorldDiamondWandItem) configurationItemController.getConfigurationItemByClass(WorldDiamondWandItem.class);
        player.getInventory().addItem(item.getItemStack(world));
    }
}
