package dev.risas.dencore.models.kit.ability.types;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.models.kit.ability.KitAbility;
import dev.risas.dencore.models.kit.types.StunnerKit;
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
public class StunnerAbility extends KitAbility<StunnerKit> {

    private final Collection<PotionEffect> effects;

    public StunnerAbility(
            StunnerKit kit,
            DenCore plugin,
            ConfigurationSection section) {
        super(kit, plugin, section);
        this.effects = Arrays.asList(
                new PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * 5, 0),
                new PotionEffect(PotionEffectType.JUMP, 20 * 5, 0),
                new PotionEffect(PotionEffectType.CONFUSION, 20 * 5, 0),
                new PotionEffect(PotionEffectType.BLINDNESS, 20 * 5, 0)
        );
    }

    @Override
    public void call(Player player) {
//        Clan clan = clanController.getClanByMemberUUID(player.getUniqueId());
//        Collection<Player> nearbyPlayers = BukkitUtil.getNearbyPlayers(player, 5);
//        nearbyPlayers.removeIf(nearbyPlayer -> nearbyPlayer == player
//                || (clan != null && clan.isMember(nearbyPlayer.getUniqueId())));
//
//        super.call(player);
//
//        nearbyPlayers.forEach(nearbyPlayer -> {
//            nearbyPlayer.addPotionEffects(effects);
//            ChatUtil.sendMessage(nearbyPlayer, "&aHas sido aturdido por: &e" + player.getName());
//            ChatUtil.sendMessage(player, "&aHas aturdido a: &e" + nearbyPlayer.getName());
//        });
    }
}
