package dev.risas.dencore.controllers;

import dev.risas.dencore.models.staff.StaffPlayer;
import dev.risas.dencore.utilities.ChatUtil;
import dev.risas.dencore.utilities.FileConfig;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StaffController {

    private final FileConfig configFile;
    private final Map<UUID, StaffPlayer> staffs;

    public StaffController(FileConfig configFile) {
        this.configFile = configFile;
        this.staffs = new HashMap<>();
    }

    public void addStaffPlayer(UUID uuid, String name) {
        this.staffs.put(uuid, new StaffPlayer(uuid, name));
    }

    public void removeStaffPlayer(UUID uuid) {
        this.staffs.remove(uuid);
    }

    public StaffPlayer getStaffPlayer(Player player) {
        if (staffs.containsKey(player.getUniqueId())) {
            return staffs.get(player.getUniqueId());
        }

        StaffPlayer staffPlayer = null;

        if (player.hasPermission("dencore.staff")) {
            staffPlayer = new StaffPlayer(player.getUniqueId(), player.getName());
            staffs.put(player.getUniqueId(), staffPlayer);
        }

        return staffPlayer;
    }

    public void sendSpyMessage(String text) {
        for (StaffPlayer staffPlayer : staffs.values()) {
            if (!staffPlayer.isSpyChat()) continue;

            Player player = staffPlayer.getPlayer();
            ChatUtil.sendMessage(player, text);
        }
    }
}
