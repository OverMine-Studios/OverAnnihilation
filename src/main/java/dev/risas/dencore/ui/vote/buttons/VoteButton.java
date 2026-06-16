package dev.risas.dencore.ui.vote.buttons;

import dev.risas.dencore.controllers.GameController;
import dev.risas.dencore.controllers.VoteController;
import dev.risas.dencore.models.vote.VoteGame;
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
 * @date 08-06-2025
 * @discord https://risas.me/discord
 */
public class VoteButton extends Button {

    private final World world;
    private final VoteController voteController;
    private final GameController gameController;

    public VoteButton(World world, VoteController voteController, GameController gameController) {
        this.world = world;
        this.voteController = voteController;
        this.gameController = gameController;
    }

    @Override
    public ItemStack getItemStack(Player player) {
        String worldName = world.getName();
        VoteGame voteGame = voteController.getVoteGame(worldName);

        return new ItemBuilder(Material.EMPTY_MAP)
                .setDisplayName("&6" + worldName)
                .setLore(
                        "&7Votos: &f" + voteGame.getVoteCount(),
                        "",
                        "&e¡Haz clic para votar por este mapa!"
                )
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if (gameController.isGameRunning()) {
            playFailure(player);
            ChatUtil.sendBroadcast("&cYa se seleccionó un mapa, no puedes votar ahora.");
            return;
        }

        String worldName = world.getName();
        VoteGame voteGame = voteController.getVoteGame(worldName);

        if (voteGame.hasVoted(worldName, player.getUniqueId())) {
            playFailure(player);
            ChatUtil.sendMessage(player, "&cYa has votado por el mapa &6" + worldName + "&c.");
            return;
        }

        playNeutral(player);

        voteController.removeVoteGamePlayer(player.getUniqueId());
        voteGame.addVote(player.getUniqueId());

        ChatUtil.sendBroadcast("&a" + player.getName() + " &eha votado por el mapa &6" + worldName + "&e.");
    }
}
