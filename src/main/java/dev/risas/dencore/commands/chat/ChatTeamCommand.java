package dev.risas.dencore.commands.chat;

import dev.risas.dencore.controllers.ChatController;
import dev.risas.dencore.utilities.ChatUtil;
import dev.risas.dencore.utilities.command.BaseCommand;
import dev.risas.dencore.utilities.command.Command;
import dev.risas.dencore.utilities.command.CommandArgs;

public class ChatTeamCommand extends BaseCommand {

    private final ChatController chatController;

    public ChatTeamCommand(ChatController chatController) {
        this.chatController = chatController;
    }

    @Override @Command(
            name = "chat.team",
            permission = "dencore.command.chat.team",
            inGameOnly = false
    )
    public void onCommand(CommandArgs command) {
        chatController.toggleTeamChat();
        ChatUtil.sendMessage(command.getSender(), "&eEl chat team se ha " + (chatController.isTeamChat() ? "&aactivado" : "&cdesactivado") + "&e.");
    }
}
