package dev.risas.dencore.commands;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.utilities.ChatUtil;
import dev.risas.dencore.utilities.command.BaseCommand;
import dev.risas.dencore.utilities.command.Command;
import dev.risas.dencore.utilities.command.CommandArgs;
import org.bukkit.command.CommandSender;

/**
 * @author Risas
 * @date 16-04-2025
 * @discord https://risas.me/discord
 */
public class DenCoreReloadCommand extends BaseCommand {

    private final DenCore plugin;

    public DenCoreReloadCommand(DenCore plugin) {
        this.plugin = plugin;
    }

    @Override @Command(
            name = "dencore.reload",
            aliases = {"den.reload"},
            permission = "dencore.command.dencore.reload"
    )
    public void onCommand(CommandArgs command) {
        CommandSender sender = command.getSender();

        plugin.onReload();
        ChatUtil.sendMessage(sender, "&aDenCore se ha recargado correctamente.");
    }
}
