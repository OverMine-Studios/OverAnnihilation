package dev.risas.dencore.listeners;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.controllers.UserController;
import dev.risas.dencore.models.user.User;
import dev.risas.dencore.utilities.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class UserListener implements Listener {

    private final DenCore plugin;
    private final UserController userController;

    public UserListener(DenCore plugin) {
        this.plugin = plugin;
        this.userController = plugin.getUserController();
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        User user = userController.createUser(event.getUniqueId(), event.getName());
        userController.loadUser(user);
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerLoginEvent(PlayerLoginEvent event) {
        User user = userController.getUser(event.getPlayer().getUniqueId());
        if (user != null) return;

        event.setResult(PlayerLoginEvent.Result.KICK_OTHER);
        event.setKickMessage(ChatUtil.translate("&c[DenCore] Error al cargar tu usuario, por favor vuelve a entrar."));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        User user = userController.getUser(event.getPlayer().getUniqueId());
        if (user == null) return;

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> userController.destroyUser(user));
    }
}
