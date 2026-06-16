package dev.risas.dencore.ui.world.buttons.world;

import dev.risas.dencore.controllers.WorldController;
import dev.risas.dencore.utilities.ChatUtil;
import dev.risas.dencore.utilities.item.ItemBuilder;
import dev.risas.dencore.utilities.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * @author Risas
 * @date 08-06-2025
 * @discord https://risas.me/discord
 */
public class WorldBackButton extends Button {

    private final WorldController worldController;

    public WorldBackButton(WorldController worldController) {
        this.worldController = worldController;
    }

    @Override
    public ItemStack getItemStack(Player player) {
        return new ItemBuilder(Material.BED)
                .setDisplayName("&cVolver")
                .setLore("&7Volver al menú anterior.")
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playNeutral(player);

        String worldName = worldController.getSelectedWorld().getName();

        if (!worldController.unloadWorld(worldName)) {
            ChatUtil.sendMessage(player, "&cNo se pudo descargar el mundo '" + worldName + "'.");
            return;
        }

        worldController.setSelectedWorld(null);
    }
}
