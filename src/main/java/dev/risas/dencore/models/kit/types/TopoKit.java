package dev.risas.dencore.models.kit.types;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.models.kit.Kit;
import dev.risas.dencore.models.kit.ability.types.TopoAbility;
import org.bukkit.configuration.ConfigurationSection;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class TopoKit extends Kit {

    public TopoKit(DenCore plugin, ConfigurationSection section) {
        super("topo", "Topo", section);
        this.ability = new TopoAbility(this, plugin, section);
    }
}
