package dev.risas.dencore.utilities.item.nbt;

import de.tr7zw.changeme.nbtapi.NBT;
import dev.risas.dencore.utilities.item.ItemBuilder;
import lombok.experimental.UtilityClass;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class NBTUtil {

    public boolean isSoulboundItem(ItemStack itemStack) {
        return NBT.get(itemStack, nbt -> (Boolean) nbt.getBoolean("souldbound"));
    }

    public boolean isUnDroppableItem(ItemStack itemStack) {
        return NBT.get(itemStack, nbt -> (Boolean) nbt.getBoolean("undroppeable"));
    }

    public ItemStack buildSoulboundItem(ItemBuilder itemBuilder) {
        return new NBTBuilder(itemBuilder.build())
                .setSoulbound(true)
                .build();
    }

    public ItemStack buildUnDroppableItem(ItemBuilder itemBuilder) {
        return new NBTBuilder(itemBuilder.build())
                .setUnDroppable(true)
                .build();
    }
}
