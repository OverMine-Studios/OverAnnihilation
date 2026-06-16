package dev.risas.dencore.models.kit.types;

import dev.risas.dencore.models.kit.Kit;
import dev.risas.dencore.models.kit.ability.types.BerserkerAbility;
import org.bukkit.configuration.ConfigurationSection;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class BerserkerKit extends Kit {

    public BerserkerKit(ConfigurationSection section) {
        super("berserker", "Berserker", section);
        this.ability = new BerserkerAbility(this);
    }
}
