package dev.risas.dencore.commands;

import dev.risas.dencore.utilities.ChatUtil;
import dev.risas.dencore.utilities.command.BaseCommand;
import dev.risas.dencore.utilities.command.Command;
import dev.risas.dencore.utilities.command.CommandArgs;

/**
 * @author Risas
 * @date 16-04-2025
 * @discord https://risas.me/discord
 */
public class DenCoreHelpCommand extends BaseCommand {

    @Override @Command(
            name = "dencore.help",
            aliases = {"den.help"},
            permission = "dencore.command.dencore.help"
    )
    public void onCommand(CommandArgs command) {
        String label = command.getLabel()
                .replace(".help", "");

        ChatUtil.sendMessage(command.getSender(), new String[]{
                ChatUtil.NORMAL_LINE,
                "&6&lDenCore Commands",
                "",
                " &f<> = Required | [] = Optional",
                "",
                " &7● &e/" + label + " reload &7- &fRecargar la configuración del plugin.",
                ChatUtil.NORMAL_LINE
        });
    }
}
