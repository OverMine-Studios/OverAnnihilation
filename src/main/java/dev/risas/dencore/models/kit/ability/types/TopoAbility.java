package dev.risas.dencore.models.kit.ability.types;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.models.kit.ability.KitAbility;
import dev.risas.dencore.models.kit.types.TopoKit;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class TopoAbility extends KitAbility<TopoKit> {

    private final Set<Material> materials;
    private final Set<UUID> users;

    public TopoAbility(TopoKit kit, DenCore plugin, ConfigurationSection section) {
        super(kit, plugin, section);
        this.materials = EnumSet.of(
                Material.DIRT,
                Material.GRASS,
                Material.STONE,
                Material.COBBLESTONE,
                Material.CLAY
        );
        this.users = new HashSet<>();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onTopoAbilityBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.CREATIVE) return;

        Block block = event.getBlock();

        if (!materials.contains(block.getType()) || !users.add(player.getUniqueId())) return;

        try {
            int y = block.getY();
            int cx = block.getX();
            int cz = block.getZ();

            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    Block target = block.getWorld().getBlockAt(cx + dx, y, cz + dz);

                    if (dx == 0 && dz == 0) continue;
                    if (!materials.contains(target.getType())) continue;
                    if (!canBreak(player, target)) continue;

                    ItemStack tool = player.getInventory().getItemInHand();
                    target.breakNaturally(tool);
                }
            }
        } finally {
            users.remove(player.getUniqueId());
        }
    }

    private boolean canBreak(Player player, Block block) {
        BlockBreakEvent call = new BlockBreakEvent(block, player);
        Bukkit.getPluginManager().callEvent(call);
        return !call.isCancelled();
    }
}
