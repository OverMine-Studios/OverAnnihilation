package dev.risas.dencore.models.kit.ability.types;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.controllers.KitController;
import dev.risas.dencore.controllers.UserController;
import dev.risas.dencore.models.kit.Kit;
import dev.risas.dencore.models.kit.ability.KitAbility;
import dev.risas.dencore.models.kit.types.FlashKit;
import dev.risas.dencore.models.user.User;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class FlashAbility extends KitAbility<FlashKit> {

    private final DenCore plugin;
    private final UserController userController;
    private final KitController kitController;

    public FlashAbility(
            FlashKit kit,
            DenCore plugin,
            UserController userController,
            KitController kitController,
            ConfigurationSection section) {
        super(kit, plugin, section);
        this.plugin = plugin;
        this.userController = userController;
        this.kitController = kitController;
    }

    @Override
    public void call(Player player) {
        player.setWalkSpeed(0.3F);
        Bukkit.getScheduler().runTaskLater(plugin, () -> player.setWalkSpeed(0.2F), 20L * 5L);

        super.call(player);
    }

    @EventHandler
    public void onFlashKitQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        User user = userController.getUser(player.getUniqueId());
        if (user == null) return;

        Kit kit = user.getKit(kitController);
        if (!(kit instanceof FlashKit)) return;

        player.setWalkSpeed(0.2F);
    }
}
