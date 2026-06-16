package dev.risas.dencore.models.configuration.types;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.controllers.TeamController;
import dev.risas.dencore.controllers.WorldController;
import dev.risas.dencore.models.configuration.ConfigurationItem;
import dev.risas.dencore.models.team.Team;
import dev.risas.dencore.models.team.TeamAreaSelection;
import dev.risas.dencore.models.team.TeamType;
import dev.risas.dencore.models.world.World;
import dev.risas.dencore.utilities.ChatUtil;
import dev.risas.dencore.utilities.item.ItemBuilder;
import dev.risas.dencore.utilities.item.nbt.NBTBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Risas
 * @date 19-05-2025
 * @discord https://risas.me/discord
 */

public class WorldAreaWandItem extends ConfigurationItem {

    private final DenCore plugin;
    private final WorldController worldController;
    private final TeamController teamController;

    public WorldAreaWandItem(String id, DenCore plugin, WorldController worldController, TeamController teamController) {
        super(id);
        this.plugin = plugin;
        this.worldController = worldController;
        this.teamController = teamController;
    }

    public ItemStack getItemStack(World world, Team team) {
        Map<String, String> properties = new HashMap<>();
        properties.put("world", world.getName());
        properties.put("team", team.getType().name());

        ItemStack itemStack = new ItemBuilder(Material.STICK)
                .setDisplayName(team.getBukkitColor() + "Area Team Wand")
                .setLore(
                        "&7Clic izquierdo: &aSeleccionar la localización #1",
                        "&7Clic derecho: &cSeleccionar la localización #2",
                        "&7Shift + clic izquierdo: &eGuardar la selección de área"
                )
                .build();

        return new NBTBuilder(itemStack)
                .setSoulbound(true)
                .setConfigurationItem(getId())
                .setProperty(properties)
                .build();
    }

    @Override
    public void call(Player player, Action action, ItemStack itemStack) {
        if (action == Action.LEFT_CLICK_AIR && player.isSneaking()) {
            TeamAreaSelection selection = TeamAreaSelection.createOrGetSelection(plugin, player);

            if (selection.isFullSelected()) {
                World world = worldController.getWorld(getProperty(itemStack, "world"));
                Team team = teamController.getTeam(TeamType.valueOf(getProperty(itemStack, "team")));

                world.setArea(team.getType(), selection.getCuboid());
                worldController.saveWorld(world);

                selection.clear(plugin, player);
                ChatUtil.sendMessage(player, "&eSelección de área guardada para el equipo " + team.getNameColored() + "&e.");
            }
            else {
                ChatUtil.sendMessage(player, "&cDebes seleccionar ambas localizaciones antes de guardar.");
            }
        }
    }

    @Override
    public void call(Player player, Action action, ItemStack itemStack, Block block) {
        TeamAreaSelection selection = TeamAreaSelection.createOrGetSelection(plugin, player);
        String label;

        switch (action) {
            case RIGHT_CLICK_BLOCK:
                selection.setLocation1(block.getLocation());
                label = "2";
                break;
            case LEFT_CLICK_BLOCK:
                selection.setLocation2(block.getLocation());
                label = "1";
                break;
            default:
                return;
        }

        String message = "&6[Area] &eLocalización &7#" + label + " &f" +
                block.getX() + ", " + block.getY() + ", " + block.getZ() + " &eha sido establecida.";

        if (selection.isFullSelected()) {
            message += " &7(" + selection.getCuboid().volume() + ")";
        }

        ChatUtil.sendMessage(player, message);
    }
}
