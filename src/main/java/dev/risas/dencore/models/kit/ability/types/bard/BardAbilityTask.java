package dev.risas.dencore.models.kit.ability.types.bard;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.models.kit.ability.types.BardAbility;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Risas
 * @date 18-05-2025
 * @discord https://risas.me/discord
 */
public class BardAbilityTask extends BukkitRunnable {

    private final DenCore plugin;

    private final BardAbility ability;
    private final Player player;
    private final Location location;
    private final int range;
    private final PotionEffect potionEffect;
    private int countdown;

    public BardAbilityTask(
            DenCore plugin,
            BardAbility ability,
            Player player,
            Location location) {
        this.plugin = plugin;
        this.ability = ability;
        this.player = player;
        this.location = location;
        this.range = 8;
        this.countdown = 10;
        this.potionEffect = new PotionEffect(PotionEffectType.REGENERATION, 20 * 10, 1);
    }

    @Override
    public void run() {
        if (countdown <= 0 || !player.isOnline()) {
            cancel();
            return;
        }

//        Clan clan = clanController.getClanByMemberUUID(player.getUniqueId());
//        Collection<Player> nearbyPlayers = location.getWorld().getNearbyEntities(location, range, range, range)
//                .stream()
//                .filter(entity -> entity instanceof Player)
//                .map(entity -> (Player) entity)
//                .filter(entityPlayer -> entityPlayer.equals(player) || (clan != null && clan.isMember(entityPlayer.getUniqueId())))
//                .collect(Collectors.toList());
//
//        nearbyPlayers.forEach(nearbyPlayer -> nearbyPlayer.addPotionEffect(potionEffect));
        countdown--;
    }

    public void start() {
        Bukkit.getScheduler().runTask(plugin, () -> ability.build(location));
        this.runTaskTimer(plugin, 0L, 20L);
    }

    public void cancel() {
        ability.unBuild(location);
        super.cancel();
    }
}
