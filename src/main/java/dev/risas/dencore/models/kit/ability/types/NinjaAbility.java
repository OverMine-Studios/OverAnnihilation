package dev.risas.dencore.models.kit.ability.types;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.models.kit.ability.KitAbility;
import dev.risas.dencore.models.kit.types.NinjaKit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class NinjaAbility extends KitAbility<NinjaKit> {

    private final Collection<PotionEffect> effects;

    public NinjaAbility(NinjaKit kit, DenCore plugin, ConfigurationSection section) {
        super(kit, plugin, section);
        this.effects = Arrays.asList(
                new PotionEffect(PotionEffectType.JUMP, 20 * 5, 2),
                new PotionEffect(PotionEffectType.SPEED, 20 * 5, 0)
        );
    }

    @Override
    public void call(Player player) {
        player.addPotionEffects(effects);
        super.call(player);
    }
}
