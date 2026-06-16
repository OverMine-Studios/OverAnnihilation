package dev.risas.dencore.models.kit.types;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.models.kit.Kit;
import dev.risas.dencore.models.kit.ability.types.StunnerAbility;
import org.bukkit.configuration.ConfigurationSection;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class StunnerKit extends Kit {

    public StunnerKit(
            DenCore plugin,
            ConfigurationSection section) {
        super("stunner", "Stunner", section);
        this.ability = new StunnerAbility(this, plugin, section);
    }
}
