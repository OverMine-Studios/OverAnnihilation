package dev.risas.dencore.commands.kit;

import dev.risas.dencore.controllers.KitController;
import dev.risas.dencore.controllers.UserController;
import dev.risas.dencore.ui.kit.KitMenu;
import dev.risas.dencore.utilities.command.BaseCommand;
import dev.risas.dencore.utilities.command.Command;
import dev.risas.dencore.utilities.command.CommandArgs;
import dev.risas.deneconomy.DenEconomyAPI;

/**
 * @author Risas
 * @date 23-05-2025
 * @discord https://risas.me/discord
 */
public class KitCommand extends BaseCommand {

    private final UserController userController;
    private final KitController kitController;
    private final DenEconomyAPI denEconomyAPI;

    public KitCommand(
            UserController userController,
            KitController kitController,
            DenEconomyAPI denEconomyAPI) {
        this.userController = userController;
        this.kitController = kitController;
        this.denEconomyAPI = denEconomyAPI;
    }

    @Override @Command(
            name = "kit",
            aliases = {"kits"}
    )
    public void onCommand(CommandArgs command) {
        KitMenu kitMenu = new KitMenu(command.getPlayer(), userController, kitController, denEconomyAPI);
        kitMenu.open();
    }
}
