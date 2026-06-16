package dev.risas.dencore.commands.chat;

import dev.risas.dencore.controllers.ChatController;
import dev.risas.dencore.utilities.ChatUtil;
import dev.risas.dencore.utilities.command.BaseCommand;
import dev.risas.dencore.utilities.command.Command;
import dev.risas.dencore.utilities.command.CommandArgs;

public class ChatGlobalCommand extends BaseCommand {

    private final ChatController chatController;

    public ChatGlobalCommand(ChatController chatController) {
        this.chatController = chatController;
    }

    @Override @Command(
            name = "chat.global",
            permission = "dencore.command.chat.global",
            inGameOnly = false
    )
    public void onCommand(CommandArgs command) {
        chatController.toggleGlobalChat();
        ChatUtil.sendMessage(command.getSender(), "&eEl chat global se ha " + (chatController.isGlobalChat() ? "&aactivado" : "&cdesactivado") + "&e.");
    }
}
