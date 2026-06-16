package dev.risas.dencore.models.kit.types;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.models.kit.Kit;
import dev.risas.dencore.models.kit.ability.types.DefensorAbility;
import org.bukkit.configuration.ConfigurationSection;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class DefensorKit extends Kit {

    public DefensorKit(DenCore plugin, ConfigurationSection section) {
        super("defensor", "Defensor", section);
        this.ability = new DefensorAbility(this, plugin, section);
    }
}
