package dev.risas.dencore.ui.team.buttons;

import dev.risas.dencore.controllers.TeamController;
import dev.risas.dencore.models.team.Team;
import dev.risas.dencore.utilities.ChatUtil;
import dev.risas.dencore.utilities.item.ItemBuilder;
import dev.risas.dencore.utilities.menu.Button;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class TeamResetButton extends Button {

    private final TeamController teamController;

    public TeamResetButton(TeamController teamController) {
        this.teamController = teamController;
    }

    @Override
    public ItemStack getItemStack(Player player) {
        return new ItemBuilder(Material.WOOL)
                .setDisplayName("&4Salir del equipo")
                .setLore("&7Haz clic para salirte del equipo actual.")
                .build();
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        if (!teamController.hasTeamSelection(player)) {
            playFailure(player);
            ChatUtil.sendMessage(player, "&cNo estas en ningun equipo.");
            return;
        }

        playNeutral(player);

        Team team = teamController.getTeamSelectionByPlayer(player);
        teamController.removeTeamSelection(team, player);
        ChatUtil.sendMessage(player, "&eTe has salido del equipo " + team.getNameColored() + "&e.");
    }
}
