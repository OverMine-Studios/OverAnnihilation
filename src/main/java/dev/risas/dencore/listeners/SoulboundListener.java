package dev.risas.dencore.listeners;

import dev.risas.dencore.utilities.item.nbt.NBTUtil;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class SoulboundListener implements Listener {

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        Item item = event.getItemDrop();
        ItemStack itemStack = item.getItemStack();

        if (NBTUtil.isUnDroppableItem(itemStack)) {
            event.setCancelled(true);
            return;
        }

        if (NBTUtil.isSoulboundItem(itemStack)) {
            player.playSound(player.getLocation(), Sound.BLAZE_HIT, 1F, 10F);
            item.remove();
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        List<ItemStack> drops = event.getDrops();
        drops.removeIf(itemStack -> NBTUtil.isSoulboundItem(itemStack) || NBTUtil.isUnDroppableItem(itemStack));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDeath(EntityDeathEvent event) {
        List<ItemStack> drops = event.getDrops();
        drops.removeIf(itemStack -> NBTUtil.isSoulboundItem(itemStack) || NBTUtil.isUnDroppableItem(itemStack));
    }
}
