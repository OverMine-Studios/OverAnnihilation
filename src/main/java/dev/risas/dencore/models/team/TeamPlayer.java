package dev.risas.dencore.models.team;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * @author Risas
 * @date 06-04-2025
 * @discord https://risas.me/discord
 */

@Getter @Setter
public class TeamPlayer {

    private final UUID uuid;
    private String name;
    private TeamPlayerStatus status;
    private Team team;
    private int kills;

    public TeamPlayer(UUID uuid, String name, Team team) {
        this.uuid = uuid;
        this.name = name;
        this.status = TeamPlayerStatus.ALIVE;
        this.team = team;
    }

    public void addKill() {
        this.kills++;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }
}
