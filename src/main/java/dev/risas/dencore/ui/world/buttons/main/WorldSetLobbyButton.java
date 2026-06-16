package dev.risas.dencore.ui.world.buttons.main;

import dev.risas.dencore.controllers.LobbyController;
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
public class WorldSetLobbyButton extends Button {

    private final LobbyController lobbyController;

    public WorldSetLobbyButton(LobbyController lobbyController) {
        this.lobbyController = lobbyController;
    }

    @Override
    public ItemStack getItemStack(Player player) {
        return new ItemBuilder(Material.COMPASS)
                .setDisplayName("&6Set Lobby")
                .setLore("&7Clic para establecer el lobby principal.")
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playNeutral(player);

        lobbyController.setLocation(player.getLocation());
        ChatUtil.sendMessage(player, "&aLobby establecido correctamente.");
    }
}
