package dev.risas.dencore.models.configuration.types;

import dev.risas.dencore.controllers.WorldController;
import dev.risas.dencore.models.world.World;
import dev.risas.dencore.models.configuration.ConfigurationItem;
import dev.risas.dencore.utilities.ChatUtil;
import dev.risas.dencore.utilities.item.ItemBuilder;
import dev.risas.dencore.utilities.item.nbt.NBTBuilder;
import org.bukkit.Location;
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

public class WorldDiamondWandItem extends ConfigurationItem {

    private final WorldController worldController;

    public WorldDiamondWandItem(
            String id,
            WorldController worldController) {
        super(id);
        this.worldController = worldController;
    }

    public ItemStack getItemStack(World world) {
        Map<String, String> properties = new HashMap<>();
        properties.put("world", world.getName());

        ItemStack itemStack = new ItemBuilder(Material.STICK)
                .setDisplayName("&6Diamond Wand")
                .setLore(
                        "&7Clic izquierdo: &aAgregar localización de diamante",
                        "&7Clic derecho: &cEliminar localización de diamante"
                )
                .build();

        return new NBTBuilder(itemStack)
                .setSoulbound(true)
                .setConfigurationItem(getId())
                .setProperty(properties)
                .build();
    }

    @Override
    public void call(Player player, Action action, ItemStack itemStack, Block block) {
        if (block.getType() != Material.DIAMOND_ORE) return;

        World world = worldController.getWorld(getProperty(itemStack, "world"));
        Location blockLocation = block.getLocation();

        if (isLeftAction(action)) {
            if (world.isDiamondLocation(blockLocation)) {
                ChatUtil.sendMessage(player, "&cEsta localización de diamante ya existe.");
                return;
            }

            world.addDiamondLocation(blockLocation);
            ChatUtil.sendMessage(player, "&eHas &aagregado &euna nueva localización de diamante.");
        }
        else if (isRightAction(action)) {
            if (!world.isDiamondLocation(blockLocation)) {
                ChatUtil.sendMessage(player, "&cEsta localización de diamante no existe.");
                return;
            }

            world.removeDiamondLocation(blockLocation);
            ChatUtil.sendMessage(player, "&eHas &cremovido &euna localización de diamante.");
        }

        worldController.saveWorld(world);
    }
}
