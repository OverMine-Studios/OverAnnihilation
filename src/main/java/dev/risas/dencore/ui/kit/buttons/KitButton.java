package dev.risas.dencore.ui.kit.buttons;

import dev.risas.dencore.controllers.UserController;
import dev.risas.dencore.models.kit.Kit;
import dev.risas.dencore.models.user.User;
import dev.risas.dencore.utilities.ChatUtil;
import dev.risas.dencore.utilities.menu.Button;
import dev.risas.deneconomy.DenEconomyAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class KitButton extends Button {

    private final Kit kit;
    private final UserController userController;
    private final DenEconomyAPI denEconomyAPI;

    public KitButton(
            Kit kit,
            UserController userController,
            DenEconomyAPI denEconomyAPI) {
        this.kit = kit;
        this.userController = userController;
        this.denEconomyAPI = denEconomyAPI;
    }

    @Override
    public ItemStack getItemStack(Player player) {
        return kit.getIcon(player);
    }

    @Override
    public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
        User user = userController.getUser(player.getUniqueId());

        if (!kit.isUnlocked(player)) {
            int coins = denEconomyAPI.getFloritos(player.getUniqueId()), price = kit.getPrice();

            if (coins < price) {
                playFailure(player);
                ChatUtil.sendMessage(player, "&cNo tienes las coins suficientes para comprar este kit.");
                return;
            }

            playNeutral(player);

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + player.getName() + " permission set " + kit.getPermission());

            denEconomyAPI.removeFloritos(player.getUniqueId(), price);
            ChatUtil.sendMessage(player, "&aHas comprado el kit &f" + kit.getName() + "&a.");
            return;
        }

        playNeutral(player);
        player.closeInventory();

        user.setSelectedKit(kit.getId());
        userController.saveUser(user);

        ChatUtil.sendMessage(player, "&eHas seleccionado el kit &f" + kit.getName() + "&e.");
    }

    @Override
    public boolean isCloseableAfterClick() {
        return false;
    }
}
