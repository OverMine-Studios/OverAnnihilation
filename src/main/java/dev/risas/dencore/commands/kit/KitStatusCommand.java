package dev.risas.dencore.commands.kit;

import dev.risas.dencore.utilities.command.BaseCommand;
import dev.risas.dencore.utilities.command.Command;
import dev.risas.dencore.utilities.command.CommandArgs;

/**
 * @author Risas
 * @date 15-11-2025
 * @discord https://risas.me/discord
 */
public class KitStatusCommand extends BaseCommand {

    @Override @Command(
            name = "kit.status",
            aliases = {"kits.status"},
            permission = "dencore.command.kit.status",
            inGameOnly = false
    )
    public void onCommand(CommandArgs command) {

    }
}
