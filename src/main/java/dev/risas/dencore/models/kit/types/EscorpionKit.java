package dev.risas.dencore.models.kit.types;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.models.kit.Kit;
import dev.risas.dencore.models.kit.ability.types.EscorpionAbility;
import org.bukkit.configuration.ConfigurationSection;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class EscorpionKit extends Kit {

    public EscorpionKit(DenCore plugin, ConfigurationSection section) {
        super("escorpion", "Escorpion", section);
        this.ability = new EscorpionAbility(this, plugin, section);
    }
}
