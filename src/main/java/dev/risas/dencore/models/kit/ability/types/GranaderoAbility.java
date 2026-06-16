package dev.risas.dencore.models.kit.ability.types;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.models.kit.ability.KitAbility;
import dev.risas.dencore.models.kit.types.GranaderoKit;
import dev.risas.dencore.utilities.TaskUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class GranaderoAbility extends KitAbility<GranaderoKit> {

    public GranaderoAbility(
            GranaderoKit kit,
            DenCore plugin,
            ConfigurationSection section) {
        super(kit, plugin, section);
    }

    @Override
    public void call(Player player) {
        Location location = player.getEyeLocation();
        Vector velocity = location.getDirection().multiply(1.3);
        Item item = player.getWorld().dropItem(location, player.getItemInHand());

        item.setPickupDelay(Integer.MAX_VALUE);
        item.setVelocity(velocity);

        TaskUtil.runLater(() -> {
            if (!item.isDead()) {
                Location itemLocation = item.getLocation();
                World itemWorld = itemLocation.getWorld();
                itemWorld.createExplosion(itemLocation, 0F);

                double radius = 5.0;
                Collection<Player> nearbyPlayers = itemWorld.getNearbyEntities(itemLocation, radius, radius, radius)
                        .stream()
                        .filter(entity -> entity instanceof Player)
                        .map(entity -> (Player) entity)
                        .collect(Collectors.toList());

//                Clan clan = clanController.getClanByMemberUUID(player.getUniqueId());
//
//                nearbyPlayers.forEach(nearbyPlayer -> {
//                    if (!nearbyPlayer.equals(player) && (clan == null || !clan.isMember(nearbyPlayer.getUniqueId()))) {
//                        nearbyPlayer.damage(8.0, player);
//                    }
//
//                    Vector impulse = nearbyPlayer.getLocation().toVector().subtract(itemLocation.toVector())
//                            .normalize().multiply(1.2);
//                    impulse.setY(1);
//                    nearbyPlayer.setVelocity(impulse);
//                });

                item.remove();
            }
        }, 20L * 3L);

        super.call(player);
    }
}
