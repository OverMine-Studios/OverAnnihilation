package dev.risas.dencore.listeners;

import dev.risas.dencore.controllers.GameController;
import dev.risas.dencore.controllers.VoteController;
import dev.risas.dencore.models.game.Game;
import dev.risas.dencore.models.game.GameStatus;
import dev.risas.dencore.models.vote.VoteGame;
import dev.risas.dencore.utilities.FileConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Risas
 * @date 09-06-2025
 * @discord https://risas.me/discord
 */
public class VoteListener implements Listener {

    private final FileConfig configFile;
    private final VoteController voteController;
    private final GameController gameController;

    public VoteListener(
            FileConfig configFile,
            VoteController voteController,
            GameController gameController) {
        this.configFile = configFile;
        this.voteController = voteController;
        this.gameController = gameController;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Game game = gameController.getGame();

        if (game.getStatus() != GameStatus.WAITING) return;
        if (game.getRequiredPlayers(configFile) > 0) return;

        gameController.startGame(null);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (gameController.isGameRunning()) return;

        Player player = event.getPlayer();

        VoteGame voteGame = voteController.getVoteGameByPlayer(player.getUniqueId());
        if (voteGame == null) return;

        voteGame.removeVote(player.getUniqueId());
    }
}
