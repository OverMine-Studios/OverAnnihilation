package dev.risas.dencore.ui.world.buttons.main;

import dev.risas.dencore.controllers.LobbyController;
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
public class WorldLobbyButton extends Button {

    private final LobbyController lobbyController;

    public WorldLobbyButton(LobbyController lobbyController) {
        this.lobbyController = lobbyController;
    }

    @Override
    public ItemStack getItemStack(Player player) {
        return new ItemBuilder(Material.FEATHER)
                .setDisplayName("&6Teleport Lobby")
                .setLore("&7Clic para teletransportarse al lobby principal.")
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playNeutral(player);
        player.teleport(lobbyController.getLocation(player));
    }
}
