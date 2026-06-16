package dev.risas.dencore.ui.world.buttons.phases;

import dev.risas.dencore.controllers.WorldController;
import dev.risas.dencore.models.world.World;
import dev.risas.dencore.utilities.ChatUtil;
import dev.risas.dencore.utilities.item.ItemBuilder;
import dev.risas.dencore.utilities.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * @author Risas
 * @date 24-05-2025
 * @discord https://risas.me/discord
 */
public class ArenaPhaseSaveButton extends Button {

    private final World world;
    private final WorldController worldController;

    public ArenaPhaseSaveButton(World world, WorldController worldController) {
        this.world = world;
        this.worldController = worldController;
    }

    @Override
    public ItemStack getItemStack(Player player) {
        return new ItemBuilder(Material.WOOL)
                .setDisplayName("&aGuardar World")
                .setLore(
                        "&7Guarda los datos de la world actual.",
                        "",
                        "&eHaz clic para guardar la world."
                )
                .setData(5)
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playNeutral(player);
        worldController.saveWorld(world);

        ChatUtil.sendMessage(player, "&eEl world &f' " + world.getName() + "' &eha sido guardada.");
    }

    @Override
    public boolean isCloseableAfterClick() {
        return false;
    }
}
