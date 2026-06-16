package dev.risas.dencore.commands.chat;

import dev.risas.dencore.utilities.ChatUtil;
import dev.risas.dencore.utilities.command.BaseCommand;
import dev.risas.dencore.utilities.command.Command;
import dev.risas.dencore.utilities.command.CommandArgs;

public class ChatCommand extends BaseCommand {

    @Override @Command(
            name = "chat",
            permission = "dencore.command.chat"
    )
    public void onCommand(CommandArgs command) {
        String label = command.getLabel();
        ChatUtil.sendMessage(command.getSender(), new String[]{
                ChatUtil.NORMAL_LINE,
                "&6&lChat Commands",
                "",
                " &f<> = Required | [] = Optional",
                "",
                " &7● &e/" + label + " global &7- &fActiva o desactiva el chat global",
                " &7● &e/" + label + " team &7- &fActiva o desactiva el chat team",
                " &7● &e/" + label + " spy &7- &fActiva o desactiva el chat de espia",
                ChatUtil.NORMAL_LINE
        });
    }
}
