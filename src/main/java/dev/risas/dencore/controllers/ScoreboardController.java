package dev.risas.dencore.controllers;

import dev.risas.dencore.listeners.ScoreboardListener;
import dev.risas.dencore.models.scoreboard.Scoreboard;
import dev.risas.dencore.models.scoreboard.ScoreboardAdapter;
import dev.risas.dencore.models.scoreboard.ScoreboardAnimation;
import dev.risas.dencore.models.scoreboard.ScoreboardThread;
import dev.risas.dencore.utilities.FileConfig;
import dev.risas.dencore.utilities.thread.NameThreadFactory;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Getter @Setter
public class ScoreboardController {

    private final FileConfig scoreboardFile;

    private final Map<UUID, Scoreboard> boards;
    private ScheduledExecutorService executor;
    private ScoreboardAdapter adapter;
    private ScoreboardListener listener;
    private ScoreboardAnimation titleAnimation, footerAnimation;

    public ScoreboardController(FileConfig scoreboardFile) {
        this.scoreboardFile = scoreboardFile;
        this.boards = new HashMap<>();
        this.setup();
    }

    public void createScoreboardPlayer(Player player) {
        Scoreboard scoreboard = new Scoreboard(player);
        boards.put(player.getUniqueId(), scoreboard);
    }

    public void removeScoreboardPlayer(Player player) {
        Scoreboard scoreboard = boards.remove(player.getUniqueId());
        if (scoreboard != null) scoreboard.delete();
    }

    public void setup() {
        if (executor != null) {
            executor.shutdown();
            executor = null;
        }

        this.onReload();

        this.executor = Executors.newScheduledThreadPool(1, new NameThreadFactory("DenCore-Scoreboard"));
        this.executor.scheduleAtFixedRate(new ScoreboardThread(this), 0L, 100L, TimeUnit.MILLISECONDS);
    }

    public void onDisable() {
        if (executor != null) {
            executor.shutdown();
            executor = null;
        }
    }

    public void onReload() {
        this.titleAnimation = new ScoreboardAnimation(
                scoreboardFile.getStringList("title-animation.lines"),
                scoreboardFile.getInt("title-animation.interval"));
        this.footerAnimation = new ScoreboardAnimation(
                scoreboardFile.getStringList("footer-animation.lines"),
                scoreboardFile.getInt("footer-animation.interval"));
    }
}
