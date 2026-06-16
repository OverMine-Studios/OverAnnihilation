package dev.risas.dencore.models.kit;

import dev.risas.dencore.models.kit.ability.KitAbility;
import dev.risas.dencore.models.kit.ability.types.BerserkerAbility;
import dev.risas.dencore.utilities.item.ItemBuilder;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public abstract class Kit {

    protected final boolean enabled;
    protected final String id, name, displayName;
    protected final List<String> purchaseLore, noPurchaseLore;
    protected final Material material;
    protected KitAbility<? extends Kit> ability;
    protected final int data, price;
    protected Map<String, String> settings;

    public Kit(String id, String name, ConfigurationSection section) {
        this.id = id;
        this.name = name;
        this.displayName = section.getString("icon.name");
        this.purchaseLore = section.getStringList("icon.lore.purchase");
        this.noPurchaseLore = section.getStringList("icon.lore.no-purchase");
        this.material = Material.matchMaterial(section.getString("icon.material"));
        this.data = section.getInt("icon.data");
        this.price = section.getInt("price");
        this.enabled = section.getBoolean("enabled");

        ConfigurationSection sectionSettings = section.getConfigurationSection("settings");

        if (sectionSettings != null) {
            this.settings = sectionSettings.getValues(false)
                    .entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, entry -> String.valueOf(entry.getValue())));
        }
    }

    public ItemStack getIcon(Player player) {
        List<String> lore = isUnlocked(player) ? purchaseLore : noPurchaseLore.stream()
                .map(line -> line
                        .replace("%price%", String.valueOf(price)))
                .collect(Collectors.toList());
        return new ItemBuilder(material)
                .setDisplayName(displayName)
                .setLore(lore)
                .setData(data)
                .build();
    }

    public String getPermission() {
        return "dencore.kit." + id.toLowerCase();
    }

    public ItemStack[] getExtraItems() {
        return null;
    }

    public void apply(Player player) {
        if (ability instanceof BerserkerAbility) {
            player.setHealthScale(14);
        }

        if (hasAbility() && ability.getItem() != null) {
            player.getInventory().addItem(ability.getItem());
        }

        player.updateInventory();
    }

    public boolean isUnlocked(Player player) {
        return price == 0 || player.hasPermission(getPermission());
    }

    public boolean hasAbility() {
        return ability != null;
    }
}
