package dev.risas.dencore.models.kit.types;

import dev.risas.dencore.models.kit.Kit;
import dev.risas.dencore.utilities.item.ItemBuilder;
import dev.risas.dencore.utilities.item.nbt.NBTUtil;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class MineroKit extends Kit {

    public MineroKit(ConfigurationSection section) {
        super("minero", "Minero", section);
    }

    @Override
    public ItemStack[] getExtraItems() {
        return new ItemStack[]{
                NBTUtil.buildSoulboundItem(new ItemBuilder(Material.STONE_PICKAXE)
                        .addEnchantment(Enchantment.DIG_SPEED, 1)),
                new ItemBuilder(Material.FURNACE)
                        .build(),
                new ItemBuilder(Material.COAL)
                        .setAmount(8)
                        .build()
        };
    }
}
