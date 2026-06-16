package dev.risas.dencore.ui.kit;

import dev.risas.dencore.controllers.KitController;
import dev.risas.dencore.controllers.UserController;
import dev.risas.dencore.ui.kit.buttons.KitButton;
import dev.risas.dencore.utilities.menu.Button;
import dev.risas.dencore.utilities.menu.Menu;
import dev.risas.deneconomy.DenEconomyAPI;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class KitMenu extends Menu {

    private final UserController userController;
    private final KitController kitController;
    private final DenEconomyAPI denEconomyAPI;

    public KitMenu(
            Player player,
            UserController userController,
            KitController kitController,
            DenEconomyAPI denEconomyAPI) {
        super(player, "Selecciona tu kit", 6);
        this.userController = userController;
        this.kitController = kitController;
        this.denEconomyAPI = denEconomyAPI;
    }

    @Override
    public Map<Integer, Button> getButtons(Player player) {
        Map<Integer, Button> buttons = new HashMap<>();

        kitController.getValues()
                .forEach(kit -> buttons.put(buttons.size(), new KitButton(kit, userController, denEconomyAPI)));

        return buttons;
    }

    @Override
    public boolean isUpdateAfterClick() {
        return true;
    }
}
