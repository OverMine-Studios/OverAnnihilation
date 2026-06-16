package dev.risas.dencore.models.items;

import dev.risas.dencore.utilities.item.ItemBuilder;
import dev.risas.dencore.utilities.item.nbt.NBTUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@Getter @Setter
public abstract class ActionItem {

    protected final String id;
    protected ItemStack itemStack;
    protected int slot;

    public ActionItem(String id, ConfigurationSection section) {
        this.id = id;
        this.itemStack = NBTUtil.buildUnDroppableItem(new ItemBuilder(section.getString("item.material"))
                .setDisplayName(section.getString("item.name"))
                .setLore(section.getStringList("item.lore"))
                .setData(section.getInt("item.data")));
        this.slot = section.getInt("slot");
    }

    public boolean isSimilar(ItemStack toCheck) {
        return (toCheck != null)
                && (toCheck.getType() != Material.AIR)
                && (toCheck.hasItemMeta())
                && (toCheck.getItemMeta().getDisplayName() != null)
                && toCheck.getItemMeta().getDisplayName().equals(itemStack.getItemMeta().getDisplayName());
    }

    public abstract void onAction(Player player);
}
