package dev.risas.dencore.utilities;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * @author Risas
 * @date 21-05-2025
 * @discord https://risas.me/discord
 */

@UtilityClass
public class PlayerUtil {

    public void replaceOrGiveItemStack(PlayerInventory inventory, ItemStack itemStack) {
        String toolName = itemStack.getType().name();
        String[] toolNameArray = toolName.split("_");

        if (toolNameArray.length == 2) {
            System.out.printf("se detecto un tool");
            String toolType = toolNameArray[1];

            boolean found = false;

            for (int i = 0; i < inventory.getSize(); i++) {
                ItemStack inventoryItem = inventory.getItem(i);
                if (inventoryItem == null || inventoryItem.getType() == Material.AIR) continue;

                System.out.printf("ItemStack: " + itemStack);

                if (inventoryItem.getType().name().endsWith(toolType)) {
                    inventory.setItem(i, itemStack);
                    System.out.printf("añadido a la localización especidica del inventario " + i);
                    found = true;
                    break;
                }
            }

            if (!found) {
                inventory.addItem(itemStack);
            }
        }
        else {
            inventory.addItem(itemStack);
            System.out.printf("añadido normal");
        }
    }

    public void sendSound(Player player, Sound sound) {
        player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
    }

    public void sendTitle(Player player, String title, String subtitle) {
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
        player.sendTitle(ChatUtil.translate(title), ChatUtil.translate(subtitle));
    }

    public void sendTitleAll(String title, String subtitle) {
        Bukkit.getOnlinePlayers().forEach(player -> sendTitle(player, title, subtitle));
    }

    public void reset(Player player, boolean effects) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);

        player.setHealth(20);
        player.setFoodLevel(20);
        player.setGameMode(GameMode.SURVIVAL);

        if (effects) {
            player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
        }
    }
}
