package dev.risas.dencore.commands.game;

import dev.risas.dencore.utilities.ChatUtil;
import dev.risas.dencore.utilities.command.BaseCommand;
import dev.risas.dencore.utilities.command.Command;
import dev.risas.dencore.utilities.command.CommandArgs;

/**
 * @author Risas
 * @date 08-06-2025
 * @discord https://risas.me/discord
 */
public class GameCommand extends BaseCommand {

    @Override @Command(
            name = "game",
            permission = "dencore.command.game",
            inGameOnly = false
    )
    public void onCommand(CommandArgs command) {
        String label = command.getLabel();
        ChatUtil.sendMessage(command.getSender(), new String[]{
                ChatUtil.NORMAL_LINE,
                "&6&lGame Commands",
                "",
                " &f<> = Required | [] = Optional",
                "",
                " &7● &e/" + label + " start <game-map> &7- &fIniciar un juego en una mapa específico.",
                " &7● &e/" + label + " stop &7- &fDetener un juego en ejecución.",
                ChatUtil.NORMAL_LINE
        });
    }
}
