package dev.risas.dencore.models.configuration;

import de.tr7zw.changeme.nbtapi.NBT;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

@Getter @Setter
public abstract class ConfigurationItem {

    protected final String id;
    protected ItemStack itemStack;

    public ConfigurationItem(String id) {
        this.id = id;
    }

    public ConfigurationItem(String id, ItemStack itemStack) {
        this.id = id;
        this.itemStack = itemStack;
    }

    public String getProperty(ItemStack itemStack, String key) {
        return NBT.get(itemStack, nbt -> (String) nbt.getString(key));
    }

    public boolean isBuildable() {
        return false;
    }

    public boolean isBlockable(Action action) {
        return action.name().endsWith("BLOCK");
    }

    public boolean isRightAction(Action action) {
        return action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK;
    }

    public boolean isLeftAction(Action action) {
        return action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK;
    }

    public void call(Player player, Location blockLocation, ItemStack itemStack) {}

    public void call(Player player, Action action, ItemStack itemStack) {}

    public void call(Player player, Action action, ItemStack itemStack, Block block) {}
}
