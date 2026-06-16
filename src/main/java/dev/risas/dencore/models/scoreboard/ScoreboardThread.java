package dev.risas.dencore.models.scoreboard;

import dev.risas.dencore.controllers.ScoreboardController;
import dev.risas.dencore.utilities.ChatUtil;
import org.bukkit.entity.Player;

import java.util.List;

public class ScoreboardThread implements Runnable {

    private final ScoreboardController scoreboardController;

    public ScoreboardThread(ScoreboardController scoreboardController) {
        this.scoreboardController = scoreboardController;
    }

    @Override
    public void run() {
        ScoreboardAdapter adapter = scoreboardController.getAdapter();

        for (Scoreboard board : scoreboardController.getBoards().values()) {
            if (board == null) continue;

            Player player = board.getPlayer();
            String title = ChatUtil.translate(adapter.getTitle(player));
            board.updateTitle(title);

            List<String> newLines = adapter.getLines(player);
            board.updateLines(newLines);
        }

        scoreboardController.getTitleAnimation().tick();
        scoreboardController.getFooterAnimation().tick();
    }
}

