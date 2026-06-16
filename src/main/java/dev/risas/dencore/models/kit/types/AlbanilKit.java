package dev.risas.dencore.models.kit.types;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.models.kit.Kit;
import dev.risas.dencore.models.kit.ability.types.AlbanilAbility;
import org.bukkit.configuration.ConfigurationSection;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class AlbanilKit extends Kit {

    public AlbanilKit(DenCore plugin, ConfigurationSection section) {
        super("albanil", "Albañil", section);
        this.ability = new AlbanilAbility(this, plugin, section);
    }
}
