package dev.risas.dencore.commands.game;

import dev.risas.dencore.controllers.GameController;
import dev.risas.dencore.controllers.WorldController;
import dev.risas.dencore.models.game.GameStatus;
import dev.risas.dencore.models.world.World;
import dev.risas.dencore.utilities.ChatUtil;
import dev.risas.dencore.utilities.command.BaseCommand;
import dev.risas.dencore.utilities.command.Command;
import dev.risas.dencore.utilities.command.CommandArgs;
import org.bukkit.command.CommandSender;

/**
 * @author Risas
 * @date 08-06-2025
 * @discord https://risas.me/discord
 */
public class GameStartCommand extends BaseCommand {

    private final GameController gameController;
    private final WorldController worldController;

    public GameStartCommand(GameController gameController, WorldController worldController) {
        this.gameController = gameController;
        this.worldController = worldController;
    }

    @Override @Command(
            name = "game.start",
            permission = "dencore.command.game.start",
            inGameOnly = false
    )
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();
        String[] args = command.getArgs();

        if (args.length < 1) {
            ChatUtil.sendMessage(sender, "&cUsage: /game start <game-map>");
            return;
        }

        String worldName = args[0];
        World world = worldController.getWorld(worldName);

        if (world == null) {
            ChatUtil.sendMessage(sender, "&cWorld '" + worldName + "' does not exist.");
            return;
        }

        if (gameController.getGame().getStatus() != GameStatus.WAITING) {
            ChatUtil.sendMessage(sender, "&cUn juego ya está en progreso.");
            return;
        }

        gameController.startGame(worldName, 0);
    }
}
