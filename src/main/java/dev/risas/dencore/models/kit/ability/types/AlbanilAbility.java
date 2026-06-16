package dev.risas.dencore.models.kit.ability.types;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.models.kit.ability.KitAbility;
import dev.risas.dencore.models.kit.types.AlbanilKit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class AlbanilAbility extends KitAbility<AlbanilKit> {

    private final List<ItemStack> items;

    public AlbanilAbility(AlbanilKit kit, DenCore plugin, ConfigurationSection section) {
        super(kit, plugin, section);
        this.items = Arrays.asList(
                new ItemStack(Material.DIRT),
                new ItemStack(Material.WOOD),
                new ItemStack(Material.WOOL),
                new ItemStack(Material.BONE),
                new ItemStack(Material.INK_SACK, 0, (short) 15)
        );
    }

    @Override
    public void call(Player player) {
        int amount = ThreadLocalRandom.current().nextInt(3, 6);

        for (int i = 0; i < amount; i++) {
            ItemStack itemStack = items.get(ThreadLocalRandom.current().nextInt(0, items.size()));
            itemStack.setAmount(ThreadLocalRandom.current().nextInt(10, 64));
            player.getInventory().addItem(itemStack);
        }

        super.call(player);
    }
}
