package dev.risas.dencore.models.kit.types;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.models.kit.Kit;
import dev.risas.dencore.models.kit.ability.types.BardAbility;
import org.bukkit.configuration.ConfigurationSection;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class BardKit extends Kit {

    public BardKit(DenCore plugin, ConfigurationSection section) {
        super("bard", "Bard", section);
        this.ability = new BardAbility(this, plugin, section);
    }
}
