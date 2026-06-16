package dev.risas.dencore.models.kit.ability.types;

import dev.risas.dencore.models.kit.ability.KitAbility;
import dev.risas.dencore.models.kit.types.BerserkerKit;
import org.bukkit.entity.Player;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class BerserkerAbility extends KitAbility<BerserkerKit> {

    public BerserkerAbility(BerserkerKit kit) {
        super(kit);
    }

    @Override
    public void call(Player player) {
        double scale = player.getHealthScale();
        if (scale < 30) player.setHealthScale(scale + 2);
    }
}
