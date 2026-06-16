package dev.risas.dencore.models.team;

import dev.risas.dencore.utilities.item.ItemBuilder;
import dev.risas.dencore.utilities.item.nbt.NBTUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

/**
 * @author Risas
 * @date 06-04-2025
 * @discord https://risas.me/discord
 */

@Getter @Setter
public class Team {

    private final TeamType type;
    private final ChatColor color;
    private final Color armorColor;
    private final String name;
    private int woolData;
    private final ItemStack[] armor, tools;
    private org.bukkit.scoreboard.Team scoreboardTeam;

    public Team(
            TeamType type,
            ChatColor color,
            Color armorColor,
            String name,
            int woolData) {
        this.type = type;
        this.color = color;
        this.armorColor = armorColor;
        this.name = name;
        this.woolData = woolData;
        this.armor = new ItemStack[]{
                NBTUtil.buildSoulboundItem(new ItemBuilder(Material.LEATHER_BOOTS)
                        .setArmorColor(armorColor)),
                NBTUtil.buildSoulboundItem(new ItemBuilder(Material.LEATHER_LEGGINGS)
                        .setArmorColor(armorColor)),
                NBTUtil.buildSoulboundItem(new ItemBuilder(Material.LEATHER_CHESTPLATE)
                        .setArmorColor(armorColor)),
                NBTUtil.buildSoulboundItem(new ItemBuilder(Material.LEATHER_HELMET)
                        .setArmorColor(armorColor))
        };
        this.tools = new ItemStack[]{
                NBTUtil.buildSoulboundItem(new ItemBuilder(Material.WOOD_SWORD)),
                NBTUtil.buildSoulboundItem(new ItemBuilder(Material.WOOD_PICKAXE)),
                NBTUtil.buildSoulboundItem(new ItemBuilder(Material.WOOD_AXE)),
                NBTUtil.buildSoulboundItem(new ItemBuilder(Material.WORKBENCH)),
        };

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getMainScoreboard();

        String teamName = "dc_" + type.name().toLowerCase() + "_team";

        org.bukkit.scoreboard.Team team = scoreboard.getTeam(teamName);
        if (team != null) team.unregister();

        this.scoreboardTeam = scoreboard.registerNewTeam(teamName);
        this.scoreboardTeam.setPrefix(color.toString());
        this.scoreboardTeam.setAllowFriendlyFire(false);
        this.scoreboardTeam.setCanSeeFriendlyInvisibles(true);
    }

    public String getBukkitColor() {
        return "&" + color.getChar();
    }

    public String getNameColored() {
        return getBukkitColor() + name;
    }

    public String getTag() {
        return getBukkitColor() + "[" + name.charAt(0) + "]";
    }

    public void addScoreboardTeam(TeamPlayer teamPlayer, Player player) {
        this.scoreboardTeam.addEntry(teamPlayer.getName());
        player.setScoreboard(scoreboardTeam.getScoreboard());
    }

    public void removeScoreboardTeam(TeamPlayer teamPlayer, Player player) {
        this.scoreboardTeam.removeEntry(teamPlayer.getName());
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }
}
