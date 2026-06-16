package dev.risas.dencore.utilities.item.nbt;

import de.tr7zw.changeme.nbtapi.NBT;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * @author Risas
 * @date 20-05-2025
 * @discord https://risas.me/discord
 */
public class NBTBuilder {

    private final ItemStack itemStack;

    public NBTBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public NBTBuilder setSoulbound(boolean soulbound) {
        if (soulbound) {
            NBT.modify(itemStack, nbt -> {
                nbt.setBoolean("souldbound", true);
            });
        }
        return this;
    }

    public NBTBuilder setUnDroppable(boolean unDroppable) {
        if (unDroppable) {
            NBT.modify(itemStack, nbt -> {
                nbt.setBoolean("undroppeable", true);
            });
        }
        return this;
    }

    public NBTBuilder setConfigurationItem(String id) {
        NBT.modify(itemStack, nbt -> {
            nbt.setString("configuration_item", id);
        });
        return this;
    }

    public NBTBuilder setProperty(Map<String, String> properties) {
        NBT.modify(itemStack, nbt -> {
            for (Map.Entry<String, String> entry : properties.entrySet()) {
                nbt.setString(entry.getKey(), entry.getValue());
            }
        });
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }
}
