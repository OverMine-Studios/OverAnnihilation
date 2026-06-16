package dev.risas.dencore.commands.kit;

import dev.risas.dencore.utilities.ChatUtil;
import dev.risas.dencore.utilities.command.BaseCommand;
import dev.risas.dencore.utilities.command.Command;
import dev.risas.dencore.utilities.command.CommandArgs;

/**
 * @author Risas
 * @date 15-11-2025
 * @discord https://risas.me/discord
 */
public class KitHelpCommand extends BaseCommand {

    @Override @Command(
            name = "kit.help",
            aliases = {"kits.help"},
            permission = "dencore.command.kit.help",
            inGameOnly = false
    )
    public void onCommand(CommandArgs command) {
        String label = command.getLabel()
                .replace(".help", "");

        ChatUtil.sendMessage(command.getSender(), new String[]{
                ChatUtil.NORMAL_LINE,
                "&6&lKit Commands",
                "",
                " &f<> = Required | [] = Optional",
                "",
                " &7● &e/" + label + " status <kit> <on|off> &7- &fHabilitar o deshabilitar un kit.",
                ChatUtil.NORMAL_LINE
        });
    }
}
