package dev.risas.dencore.models.scoreboard;

import dev.risas.dencore.controllers.*;
import dev.risas.dencore.models.game.Game;
import dev.risas.dencore.models.game.GameTeam;
import dev.risas.dencore.models.kit.Kit;
import dev.risas.dencore.models.kit.ability.KitAbility;
import dev.risas.dencore.models.phase.Phase;
import dev.risas.dencore.models.team.TeamPlayer;
import dev.risas.dencore.models.user.User;
import dev.risas.dencore.utilities.ChatUtil;
import dev.risas.dencore.utilities.FileConfig;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ScoreboardProvider implements ScoreboardAdapter {

    private final FileConfig configFile;
    private final ScoreboardController scoreboardController;
    private final UserController userController;
    private final GameController gameController;
    private final KitController kitController;

    public ScoreboardProvider(
            FileConfig configFile,
            ScoreboardController scoreboardController,
            UserController userController,
            GameController gameController,
            KitController kitController) {
        this.configFile = configFile;
        this.scoreboardController = scoreboardController;
        this.userController = userController;
        this.gameController = gameController;
        this.kitController = kitController;
    }

    @Override
    public String getTitle(Player player) {
        return scoreboardController.getTitleAnimation().getCurrent();
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> lines = new ArrayList<>();

        UUID uuid = player.getUniqueId();

        User user = userController.getUser(uuid);
        Game game = gameController.getGame();

        Kit kit = user.getKit(kitController);

        switch (game.getStatus()) {
            case WAITING:
                lines.add("&7%server_time_dd/MM/yyyy% %server_time_HH:mm:ss%");
                lines.add("");
                lines.add("&fMapa&7: &4N/A");
                lines.add("&fConectados&7: &e" + Bukkit.getOnlinePlayers().size());
                lines.add("");
                lines.add("&fEsperando...");
                lines.add("&fSe necesitan &e" + game.getRequiredPlayers(configFile) + " &fjugadores");
                lines.add("&fPara iniciar la partida.");
                lines.add("");
                lines.add("&fKit&7: &e" + kit.getName());
                lines.add("");
                break;
            case STARTING:
                lines.add("&7%server_time_dd/MM/yyyy% %server_time_HH:mm:ss%");
                lines.add("");
                lines.add("&fMapa&7: &4N/A");
                lines.add("&fConectados&7: &e" + Bukkit.getOnlinePlayers().size());
                lines.add("");
                lines.add("&fIniciando partida...");
                lines.add("&fComenzará en &e" + game.getTask().getCountdownRemaining());
                lines.add("");
                lines.add("&fKit&7: &e" + kit.getName());
                lines.add("");
                break;
            case PLAYING:
                Phase phase = game.getPhase();
                GameTeam gameTeamPlayer = gameController.getGameTeam(uuid);

                lines.add("&7%server_time_dd/MM/yyyy% %server_time_HH:mm:ss%");
                lines.add("");
                lines.add("&fFase&7: &e" + phase.getNumber());
                lines.add("&fMapa&7: &e" + game.getWorld().getName());
                lines.add("&fTiempo&7: &e" + phase.getTimeRemaining());
                lines.add("");
                lines.add("&fNexos&7:");

                for (GameTeam gameTeam : game.getTeams()) {
                    if (gameTeamPlayer != null && gameTeamPlayer.equals(gameTeam)) {
                        lines.add(gameTeam.getTeam().getNameColored() + "&7: &f" + gameTeam.getNexus().getHealth() + " &7(⬅)");
                    }
                    else {
                        lines.add(gameTeam.getTeam().getNameColored() + "&7: &f" + gameTeam.getNexus().getHealth());
                    }
                }

                lines.add("");
                lines.add("&fKit&7: &e" + kit.getName());

                if (gameTeamPlayer != null) {
                    KitAbility ability = kit.getAbility();

                    if (ability != null) {
                        lines.add("&fHabilidad&7: &e" + ability.getCooldownFormatted(player));
                    }

                    lines.add("");

                    TeamPlayer teamPlayer = gameTeamPlayer.getTeamPlayer(uuid);
                    lines.add("&fAsesinatos&7: &e" + teamPlayer.getKills());
                }

                lines.add("");
                break;
            case ENDING:
                lines.add("&7%server_time_dd/MM/yyyy% %server_time_HH:mm:ss%");
                lines.add("");
                lines.add("&fJuego&7: &4Finalizando...");
                lines.add("");
                break;
        }

        lines.add(scoreboardController.getFooterAnimation().getCurrent());

        return ChatUtil.placeholder(player, lines);
    }
}
