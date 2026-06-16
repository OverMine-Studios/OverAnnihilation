package dev.risas.dencore.listeners;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.controllers.ScoreboardController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ScoreboardListener implements Listener {

    private final ScoreboardController scoreboardController;

    public ScoreboardListener(DenCore plugin) {
        this.scoreboardController = plugin.getScoreboardController();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        scoreboardController.createScoreboardPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        scoreboardController.removeScoreboardPlayer(event.getPlayer());
    }
}
