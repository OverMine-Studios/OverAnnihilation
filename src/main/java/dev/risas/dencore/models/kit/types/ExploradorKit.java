package dev.risas.dencore.models.kit.types;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.models.kit.Kit;
import dev.risas.dencore.models.kit.ability.types.ExploradorAbility;
import org.bukkit.configuration.ConfigurationSection;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class ExploradorKit extends Kit {

    public ExploradorKit(DenCore plugin, ConfigurationSection section) {
        super("explorador", "Explorador", section);
        this.ability = new ExploradorAbility(this, plugin, section);
    }
}
