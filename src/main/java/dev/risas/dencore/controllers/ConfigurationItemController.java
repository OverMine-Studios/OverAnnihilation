package dev.risas.dencore.controllers;

import de.tr7zw.changeme.nbtapi.NBT;
import dev.risas.dencore.DenCore;
import dev.risas.dencore.models.configuration.ConfigurationItem;
import dev.risas.dencore.models.configuration.types.*;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Risas
 * @date 19-05-2025
 * @discord https://risas.me/discord
 */
public class ConfigurationItemController {

    private final Map<String, ConfigurationItem> configurationItems;

    public ConfigurationItemController(
            DenCore plugin,
            WorldController worldController,
            TeamController teamController,
            LobbyController lobbyController) {
        this.configurationItems = new HashMap<>();
        this.registerConfigurationItems(
                new WorldEditorItem("world_editor", plugin, worldController, teamController, this, lobbyController),
                new WorldSpawnLocationItem("world_spawn_location", worldController, teamController),
                new WorldNexusLocationItem("world_nexus_location", worldController, teamController),
                new WorldAreaWandItem("world_area_wand", plugin, worldController, teamController),
                new WorldDiamondWandItem("world_diamond_wand", worldController)
        );
    }

    public ConfigurationItem getConfigurationItemByClass(Class<? extends ConfigurationItem> clazz) {
        for (ConfigurationItem configurationItem : configurationItems.values()) {
            if (configurationItem.getClass() == clazz) {
                return configurationItem;
            }
        }
        return null;
    }

    public ConfigurationItem getConfigurationItem(ItemStack itemStack) {
        String configurationItemId = NBT.get(itemStack, nbt -> (String) nbt.getString("configuration_item"));
        if (configurationItemId == null) return null;

        return configurationItems.get(configurationItemId);
    }

    public void registerConfigurationItems(ConfigurationItem... items) {
        for (ConfigurationItem configurationItem : items) {
            configurationItems.put(configurationItem.getId(), configurationItem);
        }
    }
}
