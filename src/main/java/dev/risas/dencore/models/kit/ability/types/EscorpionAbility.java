package dev.risas.dencore.models.kit.ability.types;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.models.kit.ability.KitAbility;
import dev.risas.dencore.models.kit.types.EscorpionKit;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class EscorpionAbility extends KitAbility<EscorpionKit> {

    public EscorpionAbility(
            EscorpionKit kit,
            DenCore plugin,
            ConfigurationSection section) {
        super(kit, plugin, section);
    }

    @Override
    public void call(Player player) {
        EnderPearl enderPearl = player.launchProjectile(EnderPearl.class);
        enderPearl.setCustomName("escorpion_pearl");
        enderPearl.setCustomNameVisible(false);
        enderPearl.setVelocity(player.getLocation().getDirection().multiply(1.5));

        super.call(player);
    }

    @EventHandler
    public void onEscorpionEnderPearlDamage(EntityDamageByEntityEvent event) {
        Entity damagerEntity = event.getDamager();
        if (!(damagerEntity instanceof EnderPearl)) return;

        String customName = damagerEntity.getCustomName();
        if (customName == null || !customName.equalsIgnoreCase("escorpion_pearl")) return;

        if (!(event.getEntity() instanceof Player)) return;

        EnderPearl enderPearl = (EnderPearl) damagerEntity;
        Player target = (Player) event.getEntity();

        if (!(enderPearl.getShooter() instanceof Player)) return;
        Player shooter = (Player) enderPearl.getShooter();

        if (shooter.equals(target)) return;

//        Clan clan = clanController.getClanByMemberUUID(shooter.getUniqueId());
//
//        if (clan != null && clan.isMember(target.getUniqueId())) {
//            shooter.teleport(target.getLocation());
//            ChatUtil.sendMessage(shooter, "&aTe has enganchado a tu compañero &e" + target.getName() + "&a.");
//        }
//        else {
//            target.teleport(shooter.getLocation());
//            ChatUtil.sendMessage(target, "&aEl jugador &e" + shooter.getName() + " &ase ha enganchado a ti.");
//        }

        shooter.playSound(shooter.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 1F);
        target.playSound(target.getLocation(), Sound.ENDERMAN_TELEPORT, 1F, 1F);

        event.setCancelled(true);
    }

    @EventHandler
    public void onEscorpionEnderPearlTeleport(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            event.setCancelled(true);
        }
    }
}
