package dev.risas.dencore.ui.world.buttons.main;

import dev.risas.dencore.controllers.WorldController;
import dev.risas.dencore.ui.world.WorldMenu;
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
public class WorldLoadWorldButton extends Button {

    private final WorldController worldController;

    public WorldLoadWorldButton(WorldController worldController) {
        this.worldController = worldController;
    }

    @Override
    public ItemStack getItemStack(Player player) {
        return new ItemBuilder(Material.GRASS)
                .setDisplayName("&6Load Worlds")
                .setLore("&7Clic para abrir el menú de mapas.")
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playNeutral(player);

        WorldMenu worldMenu = new WorldMenu(player, worldController);
        worldMenu.open();
    }

    @Override
    public boolean isCloseableAfterClick() {
        return false;
    }
}
