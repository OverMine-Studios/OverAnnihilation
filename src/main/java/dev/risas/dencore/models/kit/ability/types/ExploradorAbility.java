package dev.risas.dencore.models.kit.ability.types;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.models.kit.ability.KitAbility;
import dev.risas.dencore.models.kit.types.ExploradorKit;
import dev.risas.dencore.utilities.TaskUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class ExploradorAbility extends KitAbility<ExploradorKit> {

    private final Set<UUID> fallingPlayers;

    public ExploradorAbility(
            ExploradorKit kit,
            DenCore plugin,
            ConfigurationSection section) {
        super(kit, plugin, section);
        this.fallingPlayers = new HashSet<>();
    }

    @Override
    public boolean isCancelable() {
        return false;
    }

    @Override
    public boolean isFishable() {
        return true;
    }

    @Override
    public void call(Player player, FishHook hook) {
        Location hookLocation = hook.getLocation();
        Block block = hookLocation.clone().subtract(0, 0.5, 0).getBlock();
        if (block.getType() == Material.AIR) return;

        Location playerLocation = player.getLocation();

        Vector direction = hookLocation.toVector().subtract(playerLocation.toVector())
                .normalize()
                .multiply(2.5);
        direction.setY(0.5);

        player.setVelocity(direction);
        player.playSound(player.getLocation(), Sound.ENDERDRAGON_WINGS, 1F, 2F);

        fallingPlayers.add(player.getUniqueId());
        TaskUtil.runLater(() -> fallingPlayers.remove(player.getUniqueId()), 20L * 5L);

        super.call(player, hook);
    }

    @EventHandler
    public void onTrepadorDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL
                || !fallingPlayers.contains(player.getUniqueId())) return;

        event.setCancelled(true);
    }
}
