package dev.risas.dencore.ui.team.buttons;

import dev.risas.dencore.controllers.GameController;
import dev.risas.dencore.controllers.TeamController;
import dev.risas.dencore.models.game.GameStatus;
import dev.risas.dencore.models.nexus.Nexus;
import dev.risas.dencore.models.team.Team;
import dev.risas.dencore.utilities.ChatUtil;
import dev.risas.dencore.utilities.item.ItemBuilder;
import dev.risas.dencore.utilities.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class TeamButton extends Button {

    private final Team team;
    private final TeamController teamController;
    private final GameController gameController;

    public TeamButton(Team team, TeamController teamController, GameController gameController) {
        this.team = team;
        this.teamController = teamController;
        this.gameController = gameController;
    }

    @Override
    public ItemStack getItemStack(Player player) {
        return new ItemBuilder(Material.WOOL)
                .setData(team.getWoolData())
                .setDisplayName(team.getBukkitColor() + "Equipo " + team.getName())
                .setLore("&7Haz clic para unirte a este equipo.")
                .setAmount(teamController.getTeamPlayers(team).size())
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        playNeutral(player);

        Nexus nexus = gameController.getNexusByTeam(team);

        if (nexus != null && nexus.isDestroyed()) {
            ChatUtil.sendMessage(player, "&cEl nexo de este equipo ha sido destruido.");
            return;
        }

        if (gameController.getGame().getStatus() == GameStatus.PLAYING) {
            gameController.addGameTeam(player, team);
        }
        else {
            Team playerTeam = teamController.getTeamSelectionByPlayer(player);

            if (playerTeam != null) {
                teamController.removeTeamSelection(playerTeam, player);
            }

            teamController.addTeamSelection(team, player);
        }

        ChatUtil.sendMessage(player, "&eTe has unido al equipo " + team.getNameColored() + "&e.");
    }
}
