package dev.risas.dencore.listeners;

import dev.risas.dencore.controllers.ConfigurationItemController;
import dev.risas.dencore.models.configuration.ConfigurationItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author Risas
 * @date 19-05-2025
 * @discord https://risas.me/discord
 */
public class ConfigurationItemListener implements Listener {

    private final ConfigurationItemController configurationItemController;

    public ConfigurationItemListener(ConfigurationItemController configurationItemController) {
        this.configurationItemController = configurationItemController;
    }

    @EventHandler
    private void onConfigurableItemInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action == Action.PHYSICAL) return;

        ItemStack itemStack = event.getItem();
        if (itemStack == null || itemStack.getType() == Material.AIR) return;

        ConfigurationItem configurationItem = configurationItemController.getConfigurationItem(itemStack);
        if (configurationItem == null || configurationItem.isBuildable()) return;

        event.setCancelled(true);

        Player player = event.getPlayer();
        player.updateInventory();

        if (configurationItem.isBlockable(action)) {
            configurationItem.call(player, action, itemStack, event.getClickedBlock());
        }
        else {
            configurationItem.call(player, action, itemStack);
        }
    }

    @EventHandler
    private void onConfigurableItemPlace(BlockPlaceEvent event) {
        ItemStack itemStack = event.getItemInHand();
        if (itemStack == null || itemStack.getType() == Material.AIR) return;

        ConfigurationItem configurationItem = configurationItemController.getConfigurationItem(itemStack);
        if (configurationItem == null) return;

        event.setCancelled(true);

        configurationItem.call(event.getPlayer(), event.getBlock().getLocation(), itemStack);
    }
}
