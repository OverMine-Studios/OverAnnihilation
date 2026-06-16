package dev.risas.dencore.models.kit.types;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.models.kit.Kit;
import dev.risas.dencore.models.kit.ability.types.GranaderoAbility;
import org.bukkit.configuration.ConfigurationSection;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class GranaderoKit extends Kit {

    public GranaderoKit(DenCore plugin, ConfigurationSection section) {
        super("granadero", "Granadero", section);
        this.ability = new GranaderoAbility(this, plugin, section);
    }
}
