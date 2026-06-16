package dev.risas.dencore.models.kit.types;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.controllers.KitController;
import dev.risas.dencore.controllers.UserController;
import dev.risas.dencore.models.kit.Kit;
import dev.risas.dencore.models.kit.ability.types.FlashAbility;
import org.bukkit.configuration.ConfigurationSection;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class FlashKit extends Kit {

    public FlashKit(DenCore plugin, UserController userController, KitController kitController, ConfigurationSection section) {
        super("flash", "Flash", section);
        this.ability = new FlashAbility(this, plugin, userController, kitController, section);
    }
}
