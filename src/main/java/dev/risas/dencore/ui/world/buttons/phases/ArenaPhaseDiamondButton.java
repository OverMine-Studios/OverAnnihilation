package dev.risas.dencore.ui.world.buttons.phases;

import dev.risas.dencore.models.phase.Phase;
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
public class ArenaPhaseDiamondButton extends Button {

    private final Phase phase;

    public ArenaPhaseDiamondButton(Phase phase) {
        this.phase = phase;
    }

    @Override
    public ItemStack getItemStack(Player player) {
        return new ItemBuilder(Material.INK_SACK)
                .setDisplayName("&6Phase Diamonds")
                .setLore("&7Haz clic para &aactivar&7/&cdesactivar &7esta opción.")
                .setData(phase.isDiamonds() ? 10 : 8)
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playNeutral(player);
        phase.setDiamonds(!phase.isDiamonds());
    }

    @Override
    public boolean isCloseableAfterClick() {
        return false;
    }
}
