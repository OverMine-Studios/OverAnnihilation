package dev.risas.dencore.models.kit.ability;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.models.kit.Kit;
import dev.risas.dencore.utilities.ChatUtil;
import dev.risas.dencore.utilities.TimeUtil;
import dev.risas.dencore.utilities.item.ItemBuilder;
import dev.risas.dencore.utilities.item.nbt.NBTUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */

@Getter
public abstract class KitAbility<K extends Kit> implements Listener {

    protected final K kit;
    protected ItemStack item;
    protected final long cooldown;
    protected final Map<UUID, LocalDateTime> cooldowns;

    public KitAbility(K kit) {
        this.kit = kit;
        this.item = null;
        this.cooldown = 0L;
        this.cooldowns = new HashMap<>();
    }

    public KitAbility(K kit, DenCore plugin, ConfigurationSection section) {
        this.kit = kit;

        ConfigurationSection itemSection = section.getConfigurationSection("ability.item");

        if (itemSection != null) {
            this.item = NBTUtil.buildUnDroppableItem(new ItemBuilder(itemSection.getString("material"))
                    .setDisplayName(itemSection.getString("name"))
                    .setLore(itemSection.getStringList("lore"))
                    .setData(itemSection.getInt("data")));
        }

        this.cooldown = TimeUtil.formatLong(section.getString("ability.cooldown"));
        this.cooldowns = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public boolean isSimilar(ItemStack toCheck) {
        if (item == null) return false;

        ItemMeta toCheckMeta = toCheck.getItemMeta(), itemMeta = item.getItemMeta();
        return toCheck.getType() == item.getType()
                && toCheckMeta.getDisplayName() != null
                && toCheckMeta.getDisplayName().equals(itemMeta.getDisplayName())
                && toCheckMeta.getLore().equals(itemMeta.getLore());
    }

    public boolean isCancelable() {
        return true;
    }

    public boolean isBuildable() {
        return false;
    }

    public boolean isFishable() {
        return false;
    }

    public boolean hasCooldown(Player player) {
        return cooldowns.containsKey(player.getUniqueId())
                && !cooldowns.get(player.getUniqueId()).isBefore(LocalDateTime.now());
    }

    public void setCooldown(Player player) {
        Duration duration = Duration.ofMillis(cooldown);
        cooldowns.put(player.getUniqueId(), LocalDateTime.now().plus(duration));
    }

    public String getCooldownRemaining(Player player) {
        return TimeUtil.toFormatDuration(Duration.between(LocalDateTime.now(), cooldowns.get(player.getUniqueId())));
    }
    public String getCooldownFormatted(Player player) {
        return hasCooldown(player) ? getCooldownRemaining(player) : "Listo";
    }

    public boolean isRestricted(Player player) {
        if (hasCooldown(player)) {
            ChatUtil.sendMessage(player, "&cTienes que esperar " + getCooldownRemaining(player) + " para usar la habilidad.");
            return true;
        }

        return false;
    }

    public void call(Player player) {
        setCooldown(player);
        ChatUtil.sendMessage(player, "&aHas usado tu habilidad de kit: &r" + kit.getDisplayName() + "&a.");
    }

    public void call(Player player, Location blockLocation) {
        call(player);
    }

    public void call(Player player, FishHook fishHook) {
        call(player);
    }

    public void call(Player player, Map<String, Object> data) {}
}
