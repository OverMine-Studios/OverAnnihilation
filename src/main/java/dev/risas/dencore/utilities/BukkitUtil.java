package dev.risas.dencore.utilities;

import lombok.experimental.UtilityClass;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Risas
 * @date 18-05-2025
 * @discord https://risas.me/discord
 */

@UtilityClass
public class BukkitUtil {

    public Collection<Player> getNearbyPlayers(Player player, double radius) {
        return player.getNearbyEntities(radius, radius, radius)
                .stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity)
                .collect(Collectors.toList());
    }

    public ItemStack[] getItemStackClones(ItemStack[] itemStacks) {
        ItemStack[] clonedItemStacks = new ItemStack[itemStacks.length];

        for (int i = 0; i < itemStacks.length; i++) {
            if (itemStacks[i] != null) {
                clonedItemStacks[i] = itemStacks[i].clone();
            }
        }
        return clonedItemStacks;
    }
}
