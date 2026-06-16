package dev.risas.dencore.models.staff;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter @Setter
public class StaffPlayer {

    private final UUID uuid;
    private final String name;
    private boolean spyChat;

    public StaffPlayer(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

    public void toggleSpyChat() {
        this.spyChat = !this.spyChat;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }
}
