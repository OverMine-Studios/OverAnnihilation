package dev.risas.dencore.models.configuration.types;

import dev.risas.dencore.controllers.TeamController;
import dev.risas.dencore.controllers.WorldController;
import dev.risas.dencore.models.world.World;
import dev.risas.dencore.models.configuration.ConfigurationItem;
import dev.risas.dencore.models.team.Team;
import dev.risas.dencore.models.team.TeamType;
import dev.risas.dencore.utilities.ChatUtil;
import dev.risas.dencore.utilities.item.ItemBuilder;
import dev.risas.dencore.utilities.item.nbt.NBTBuilder;
import org.bukkit.Material;
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

public class WorldSpawnLocationItem extends ConfigurationItem {

    private final WorldController worldController;
    private final TeamController teamController;

    public WorldSpawnLocationItem(String id, WorldController worldController, TeamController teamController) {
        super(id);
        this.worldController = worldController;
        this.teamController = teamController;
    }

    public ItemStack getItemStack(World world, Team team) {
        Map<String, String> properties = new HashMap<>();
        properties.put("world", world.getName());
        properties.put("team", team.getType().name());

        ItemStack itemStack = new ItemBuilder(Material.WOOL)
                .setDisplayName(team.getBukkitColor() + "Spawn Team Location")
                .setLore(
                        "&7Pega al aire con el bloque para establecer",
                        "&7la ubicación de spawn del equipo " + team.getNameColored()
                )
                .setData(team.getWoolData())
                .build();

        return new NBTBuilder(itemStack)
                .setSoulbound(true)
                .setConfigurationItem(getId())
                .setProperty(properties)
                .build();
    }

    @Override
    public void call(Player player, Action action, ItemStack itemStack) {
        if (action == Action.LEFT_CLICK_AIR) {
            World world = worldController.getWorld(getProperty(itemStack, "world"));
            Team team = teamController.getTeam(TeamType.valueOf(getProperty(itemStack, "team")));

            world.setSpawnLocation(team.getType(), player.getLocation());
            worldController.saveWorld(world);

            ChatUtil.sendMessage(player, "&eUbicación de spawn del equipo " + team.getNameColored() + " &eestablecida.");
        }
    }
}
