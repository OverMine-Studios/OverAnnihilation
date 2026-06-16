package dev.risas.dencore.tasks;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.controllers.*;
import dev.risas.dencore.models.world.World;
import dev.risas.dencore.models.game.Game;
import dev.risas.dencore.models.game.GameStatus;
import dev.risas.dencore.models.game.GameTeam;
import dev.risas.dencore.models.phase.Phase;
import dev.risas.dencore.utilities.ChatUtil;
import dev.risas.dencore.utilities.PlayerUtil;
import dev.risas.dencore.utilities.TimeUtil;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Risas
 * @date 19-05-2025
 * @discord https://risas.me/discord
 */
public class GameTask extends BukkitRunnable {

    private int countdown;
    private String worldName;

    private final TeamController teamController;
    private final GameController gameController;
    private final VoteController voteController;
    private final WorldController worldController;

    public GameTask(
            String worldName,
            int countdown,
            TeamController teamController,
            GameController gameController,
            VoteController voteController,
            WorldController worldController) {
        this.countdown = countdown;
        this.worldName = worldName;
        this.teamController = teamController;
        this.gameController = gameController;
        this.voteController = voteController;
        this.worldController = worldController;
    }

    @Override
    public void run() {
        Game game = gameController.getGame();
        GameStatus status = game.getStatus();

        switch (status) {
            case STARTING: {
                if (countdown <= 0) {
                    if (worldName == null) {
                        worldName = voteController.getMostVotedGame();
                    }

                    game.startGame(
                            worldName,
                            worldController,
                            teamController,
                            gameController
                    );
                    return;
                }

                countdown--;

                if (countdown % 10 == 0 || countdown <= 5) {
                    PlayerUtil.sendTitleAll("&6&l" + countdown, "&fLa partida esta por comenzar.");
                }
                break;
            }
            case PLAYING: {
                Phase phase = game.getPhase();
                World world = game.getWorld();

                boolean cancelled = game.isCancelled();

                if (phase.isFinished() || cancelled) {
                    Phase nextPhase = world.getNextPhase(phase);

                    if (nextPhase == null || cancelled) {
                        countdown = 5;

                        GameTeam winner = game.getWinner();

                        if (winner != null) {
//                            winner.addElo(ConfigResource.ELO_SYSTEM_PER_WIN);
//                            winner.addCoins(ConfigResource.COINS_SYSTEM_PER_WIN);
//                            plugin.getClanController().saveClan(winner, false);
//
//                            game.sendMessage(ChatUtil.NORMAL_LINE);
//                            game.sendMessage("&6&lEstadisticas de la partida");
//                            game.sendMessage("");
//                            game.sendMessage("&eClan Ganador: &f" + winner.getName());
//                            game.sendMessage("&eHits totales: &f" + game.getTotalHits());
//                            game.sendMessage("&eKills totales: &f" + game.getTotalKills());
//                            game.sendMessage("");
//                            game.sendMessage("&6Top 3 jugadores por hits");
//
//                            List<TeamPlayer> hitsPlayers = game.getTopByHits(3);
//
//                            for (int i = 0; i < hitsPlayers.size(); i++) {
//                                TeamPlayer teamPlayer = hitsPlayers.get(i);
//                                game.sendMessage(" &7▶ &e" + (i + 1) + ". " + teamPlayer.getName() + ": &f" + teamPlayer.getHits());
//                            }
//
//                            for (GameTeam gameTeam : game.getTeams()) {
//                                game.sendMessage("");
//                                game.sendMessage("&6Estadisticas equipo " + gameTeam.getTeam().getNameColored());
//                                game.sendMessage(" &7▶ &eHits: &f" + gameTeam.getTotalHits());
//                                game.sendMessage(" &7▶ &eKills: &f" + gameTeam.getTotalKills());
//                            }
//
//                            game.sendMessage(ChatUtil.NORMAL_LINE);
                        }
                        else {
                            game.sendMessage(ChatUtil.NORMAL_LINE);
                            game.sendMessage("&6&lEstadisticas de la partida");
                            game.sendMessage("");
                            game.sendMessage("&cNo hay equipo ganador.");
                            game.sendMessage(ChatUtil.NORMAL_LINE);
                        }

                        //game.setStatus(GameStatus.ENDING);

                        if (cancelled) {
                            game.sendTitle(
                                    "&6&lPartida Cancelada",
                                    "&fLa partida ha sido cancelada.");
                            game.sendMessage("&6[Phase] &fLa partida ha sido cancelada.");
                        }
                        else {
                            game.sendTitle(
                                    "&6&lUltima Fase",
                                    "&fLa ultima fase ha terminado.");
                            game.sendMessage("&6[Phase] &fLa ultima fase ha terminado.");
                        }
                        return;
                    }

                    nextPhase.onStart(world, game);

                    game.setPhase(nextPhase);
                    game.sendTitle(
                            "&6&lPhase " + nextPhase.getNumber(),
                            "&fLa fase &e" + nextPhase.getNumber() + " &fha comenzado.");
                    game.sendMessage("&6[Phase] &fLa fase &e" + nextPhase.getNumber() + " &fha comenzado.");
                    return;
                }

                phase.decreaseTimeRemaining();
                break;
            }
            case ENDING: {
                if (countdown <= 0) {
                    game.stopGame();
                    return;
                }

                game.sendMessage("&6[World] &fLa world se reinicia en &e" + countdown + " &fsegundo(s).");
                countdown--;
                break;
            }
        }
    }

    public void start(DenCore plugin) {
        this.runTaskTimer(plugin, 0L, 20L);
    }

    public String getCountdownRemaining() {
        return TimeUtil.toFormatDurationSeconds(countdown);
    }
}
