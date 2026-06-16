package dev.risas.dencore.models.kit.types;

import dev.risas.dencore.models.kit.Kit;
import org.bukkit.configuration.ConfigurationSection;

/**
 * @author Risas
 * @date 06-06-2025
 * @discord https://risas.me/discord
 */
public class CivilKit extends Kit {

    public CivilKit(ConfigurationSection section) {
        super("civil", "Civil", section);
    }
}
