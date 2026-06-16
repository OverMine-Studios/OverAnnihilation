package dev.risas.dencore.commands.chat;

import dev.risas.dencore.controllers.StaffController;
import dev.risas.dencore.models.staff.StaffPlayer;
import dev.risas.dencore.utilities.ChatUtil;
import dev.risas.dencore.utilities.command.BaseCommand;
import dev.risas.dencore.utilities.command.Command;
import dev.risas.dencore.utilities.command.CommandArgs;
import org.bukkit.entity.Player;

public class ChatSpyCommand extends BaseCommand {

    private final StaffController staffController;

    public ChatSpyCommand(StaffController staffController) {
        this.staffController = staffController;
    }

    @Override @Command(
            name = "chat.spy",
            permission = "dencore.command.chat.spy"
    )
    public void onCommand(CommandArgs command) {
        Player player = command.getPlayer();
        StaffPlayer staffPlayer = staffController.getStaffPlayer(player);

        if (staffPlayer == null) {
            ChatUtil.sendMessage(player, "&cNo eres staff.");
            return;
        }

        staffPlayer.toggleSpyChat();
        ChatUtil.sendMessage(player, "&eEl chat de espia ha sido " + (staffPlayer.isSpyChat() ? "&aactivado" : "&cdesactivado") + "&e.");
    }
}
