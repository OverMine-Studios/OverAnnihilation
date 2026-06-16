package dev.risas.dencore.listeners;

import dev.risas.dencore.controllers.StaffController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class StaffListener implements Listener {

    private final StaffController staffController;

    public StaffListener(StaffController staffController) {
        this.staffController = staffController;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPermission("dencore.staff")) return;

        staffController.addStaffPlayer(player.getUniqueId(), player.getName());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        staffController.removeStaffPlayer(event.getPlayer().getUniqueId());
    }
}
