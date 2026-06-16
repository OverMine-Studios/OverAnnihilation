package dev.risas.dencore.controllers;

import dev.risas.dencore.utilities.FileConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ToolsController {

    private final FileConfig configFile;
    private final Map<Material, List<String>> tools;

    public ToolsController(FileConfig configFile) {
        this.configFile = configFile;
        this.tools = new HashMap<>();
        this.onReload();
    }

    public List<String> getTools(Material blockType) {
        return tools.get(blockType);
    }

    public String getToolsFormatted(List<String> tools) {
        return String.join(", ", tools);
    }

    public boolean isValidTool(List<String> tools, ItemStack itemStack) {
        String itemName = itemStack.getType().name();
        return tools.stream().anyMatch(itemName::contains);
    }

    public void onReload() {
        ConfigurationSection section = configFile.getConfiguration().getConfigurationSection("game-system.tools");
        if (section == null) throw new NullPointerException("Configuration section 'game-system.tools' is missing");

        section.getKeys(false).forEach(materialName -> {
            Material material = Material.matchMaterial(materialName);

            if (material == null) {
                Bukkit.getLogger().warning("Material '" + materialName + "' from tool section not found");
                return;
            }

            this.tools.put(material, section.getStringList(materialName));
        });
    }
}
