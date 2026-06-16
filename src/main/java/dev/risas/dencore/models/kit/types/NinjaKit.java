package dev.risas.dencore.models.kit.types;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.models.kit.Kit;
import dev.risas.dencore.models.kit.ability.types.NinjaAbility;
import org.bukkit.configuration.ConfigurationSection;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class NinjaKit extends Kit {

    public NinjaKit(DenCore plugin, ConfigurationSection section) {
        super("ninja", "Ninja", section);
        this.ability = new NinjaAbility(this, plugin, section);
    }
}
