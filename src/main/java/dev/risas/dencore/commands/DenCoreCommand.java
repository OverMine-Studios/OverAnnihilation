package dev.risas.dencore.commands;

import dev.risas.dencore.controllers.ConfigurationItemController;
import dev.risas.dencore.models.configuration.types.WorldEditorItem;
import dev.risas.dencore.utilities.ChatUtil;
import dev.risas.dencore.utilities.command.BaseCommand;
import dev.risas.dencore.utilities.command.Command;
import dev.risas.dencore.utilities.command.CommandArgs;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author Risas
 * @date 16-04-2025
 * @discord https://risas.me/discord
 */
public class DenCoreCommand extends BaseCommand {

    private final ConfigurationItemController configurationItemController;

    public DenCoreCommand(ConfigurationItemController configurationItemController) {
        this.configurationItemController = configurationItemController;
    }

    @Override @Command(
            name = "dencore",
            aliases = {"den"},
            permission = "dencore.command.dencore"
    )
    public void onCommand(CommandArgs command) {
        String[] args = command.getArgs();
        CommandSender sender = command.getSender();
        String label = command.getLabel();

        if (args.length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                WorldEditorItem worldEditorItem = (WorldEditorItem) configurationItemController.getConfigurationItemByClass(WorldEditorItem.class);
                player.getInventory().addItem(worldEditorItem.getItemStack());
                return;
            }

            ChatUtil.sendMessage(sender, "&cUsa /" + label + " help para ver los comandos disponibles.");
        }
    }
}
