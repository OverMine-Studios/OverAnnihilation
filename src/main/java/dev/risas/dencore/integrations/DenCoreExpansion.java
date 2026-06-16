package dev.risas.dencore.integrations;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.controllers.GameController;
import dev.risas.dencore.models.game.GameTeam;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DenCoreExpansion extends PlaceholderExpansion {

    private final DenCore plugin;
    private final GameController gameController;

    public DenCoreExpansion(DenCore plugin, GameController gameController) {
        this.plugin = plugin;
        this.gameController = gameController;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @NotNull String getAuthor() {
        return "Risas";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "dencore";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null) return null;

        if (identifier.equalsIgnoreCase("team_color")) {
            GameTeam gameTeam = gameController.getGameTeam(player.getUniqueId());
            return gameTeam == null ? "" : gameTeam.getTeam().getBukkitColor();
        }
        if (identifier.equalsIgnoreCase("team_name")) {
            GameTeam gameTeam = gameController.getGameTeam(player.getUniqueId());
            return gameTeam == null ? "" : gameTeam.getTeam().getNameColored();
        }

        return null;
    }
}
