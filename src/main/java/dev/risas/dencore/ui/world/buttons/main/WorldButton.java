package dev.risas.dencore.ui.world.buttons.main;

import dev.risas.dencore.controllers.WorldController;
import dev.risas.dencore.models.world.World;
import dev.risas.dencore.utilities.ChatUtil;
import dev.risas.dencore.utilities.item.ItemBuilder;
import dev.risas.dencore.utilities.menu.Button;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * @author Risas
 * @date 20-05-2025
 * @discord https://risas.me/discord
 */
public class WorldButton extends Button {

    private final World world;
    private final WorldController worldController;

    public WorldButton(
            World world,
            WorldController worldController) {
        this.world = world;
        this.worldController = worldController;
    }

    @Override
    public ItemStack getItemStack(Player player) {
        return new ItemBuilder(Material.EMPTY_MAP)
                .setDisplayName("&6" + world.getName())
                .setLore("&7Haz clic para editar este mapa.")
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playNeutral(player);

        org.bukkit.World bukkitWorld = worldController.loadWorld(world.getName());

        if (bukkitWorld == null) {
            ChatUtil.sendMessage(player, "&cNo se pudo cargar el mapa &6" + world.getName() + "&c.");
            return;
        }

        worldController.setSelectedWorld(world);

        if (player.getWorld() != bukkitWorld) {
            player.teleport(bukkitWorld.getSpawnLocation());
            player.setGameMode(GameMode.CREATIVE);
        }

        ChatUtil.sendMessage(player, "&eHas seleccionado el mapa &6" + world.getName() + "&e.");
    }
}
