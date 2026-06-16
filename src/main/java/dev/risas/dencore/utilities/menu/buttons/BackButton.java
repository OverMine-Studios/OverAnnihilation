package dev.risas.dencore.utilities.menu.buttons;

import dev.risas.dencore.utilities.item.ItemBuilder;
import dev.risas.dencore.utilities.menu.Button;
import dev.risas.dencore.utilities.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * @author Risas
 * @date 23-05-2025
 * @discord https://risas.me/discord
 */
public class BackButton extends Button {

    private final Menu menu;

    public BackButton(Menu menu) {
        this.menu = menu;
    }

    @Override
    public ItemStack getItemStack(Player player) {
        return new ItemBuilder(Material.BED)
                .setDisplayName("&cVolver")
                .setLore("&7Haz clic para volver al menú anterior.")
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playNeutral(player);
        menu.open();
    }

    @Override
    public boolean isCloseableAfterClick() {
        return false;
    }
}
