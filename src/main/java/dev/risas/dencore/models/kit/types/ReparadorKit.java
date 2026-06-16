package dev.risas.dencore.models.kit.types;

import dev.risas.dencore.models.kit.Kit;
import dev.risas.dencore.models.kit.ability.types.ReparadorAbility;
import org.bukkit.configuration.ConfigurationSection;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class ReparadorKit extends Kit {

    public ReparadorKit(ConfigurationSection section) {
        super("reparador", "Reparador", section);
        this.ability = new ReparadorAbility(this);
    }
}
