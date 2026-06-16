package dev.risas.dencore.models.items.types;

import dev.risas.dencore.controllers.KitController;
import dev.risas.dencore.controllers.UserController;
import dev.risas.dencore.models.items.ActionItem;
import dev.risas.dencore.ui.kit.KitMenu;
import dev.risas.deneconomy.DenEconomyAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class KitActionItem extends ActionItem {

    private final UserController userController;
    private final KitController kitController;
    private final DenEconomyAPI denEconomyAPI;

    public KitActionItem(
            ConfigurationSection section,
            UserController userController,
            KitController kitController,
            DenEconomyAPI denEconomyAPI) {
        super("kit", section.getConfigurationSection("kit"));
        this.userController = userController;
        this.kitController = kitController;
        this.denEconomyAPI = denEconomyAPI;
    }

    @Override
    public void onAction(Player player) {
        KitMenu kitMenu = new KitMenu(player, userController, kitController, denEconomyAPI);
        kitMenu.open();
    }
}
