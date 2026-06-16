package dev.risas.dencore.controllers;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.models.kit.Kit;
import dev.risas.dencore.models.kit.ability.KitAbility;
import dev.risas.dencore.models.kit.types.*;
import dev.risas.dencore.utilities.FileConfig;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class KitController {

    private final DenCore plugin;
    private final UserController userController;

    private final FileConfig kitsFile;
    private final Map<String, Kit> kits;

    public KitController(DenCore plugin, UserController userController) {
        this.plugin = plugin;
        this.userController = userController;
        this.kitsFile = plugin.getKitsFile();
        this.kits = new LinkedHashMap<>();
        this.onReload();
    }

    public Collection<Kit> getValues() {
        return kits.values();
    }

    public Kit getKitByName(String name) {
        return name == null ? null : kits.get(name.toLowerCase());
    }

    public Kit getKitByAbilityItem(ItemStack item) {
        return kits.values().stream()
                .filter(kit -> kit.getAbility() != null && kit.getAbility().isSimilar(item))
                .findFirst()
                .orElse(null);
    }

    public KitAbility<? extends Kit> getKitAbilityIfApplicable(ItemStack item) {
        if (item == null) return null;

        Kit kit = getKitByAbilityItem(item);
        if (kit == null || !kit.hasAbility()) return null;

        return kit.getAbility();
    }

    public void onReload() {
        this.kits.clear();

        ConfigurationSection section = kitsFile.getConfiguration();
        if (section == null) throw new NullPointerException("Kits section is null");

        //ClanController clanController = plugin.getClanController();

        this.kits.put("civil", new CivilKit(section.getConfigurationSection("civil")));
        this.kits.put("stunner", new StunnerKit(plugin, section.getConfigurationSection("stunner")));
        this.kits.put("granadero", new GranaderoKit(plugin, section.getConfigurationSection("granadero")));
        this.kits.put("escorpion", new EscorpionKit(plugin, section.getConfigurationSection("escorpion")));
        this.kits.put("flash", new FlashKit(plugin, userController, this, section.getConfigurationSection("flash")));
        this.kits.put("bard", new BardKit(plugin, section.getConfigurationSection("bard")));
        this.kits.put("explorador", new ExploradorKit(plugin, section.getConfigurationSection("explorador")));
        this.kits.put("albanil", new AlbanilKit(plugin, section.getConfigurationSection("albanil")));
        this.kits.put("ninja", new NinjaKit(plugin, section.getConfigurationSection("ninja")));
        this.kits.put("minero", new MineroKit(section.getConfigurationSection("minero")));
        this.kits.put("reparador", new ReparadorKit(section.getConfigurationSection("reparador")));
        this.kits.put("berserker", new BerserkerKit(section.getConfigurationSection("berserker")));
        this.kits.put("guerrero", new GuerreroKit(section.getConfigurationSection("guerrero")));
        this.kits.put("defensor", new DefensorKit(plugin, section.getConfigurationSection("defensor")));
        this.kits.put("topo", new TopoKit(plugin, section.getConfigurationSection("topo")));
    }
}
