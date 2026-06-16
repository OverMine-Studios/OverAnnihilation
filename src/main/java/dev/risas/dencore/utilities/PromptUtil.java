package dev.risas.dencore.utilities;

import dev.risas.dencore.DenCore;
import lombok.experimental.UtilityClass;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

@UtilityClass
public class PromptUtil {

    public void createPrompt(Player player, StringPrompt prompt) {
        Conversation conversation = new ConversationFactory(DenCore.getInstance())
                .withFirstPrompt(prompt)
                .buildConversation(player);
        conversation.begin();
    }
}
