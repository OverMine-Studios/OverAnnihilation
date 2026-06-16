package dev.risas.dencore.controllers;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatController {

    private boolean globalChat, teamChat;

    public ChatController() {
        this.globalChat = true;
        this.teamChat = true;
    }

    public void toggleGlobalChat() {
        this.globalChat = !this.globalChat;
    }

    public void toggleTeamChat() {
        this.teamChat = !this.teamChat;
    }
}
