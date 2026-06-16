package dev.risas.dencore.controllers;

import dev.risas.dencore.models.items.ActionItem;
import dev.risas.dencore.models.items.types.KitActionItem;
import dev.risas.dencore.models.items.types.VoteActionItem;
import dev.risas.dencore.models.items.types.TeamActionItem;
import dev.risas.dencore.utilities.FileConfig;
import dev.risas.dencore.utilities.PlayerUtil;
import dev.risas.dencore.utilities.SerializeUtil;
import dev.risas.deneconomy.DenEconomyAPI;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class LobbyController {

    private final FileConfig lobbyDataFile;
    private final TeamController teamController;
    private final UserController userController;
    private final KitController kitController;
    private final WorldController worldController;
    private final GameController gameController;
    private final VoteController voteController;
    private final DenEconomyAPI denEconomyAPI;

    private Location location;
    private final Map<String, ActionItem> items;

    public LobbyController(
            FileConfig lobbyDataFile,
            TeamController teamController,
            UserController userController,
            KitController kitController,
            WorldController worldController,
            GameController gameController,
            VoteController voteController,
            DenEconomyAPI denEconomyAPI) {
        this.lobbyDataFile = lobbyDataFile;
        this.teamController = teamController;
        this.userController = userController;
        this.kitController = kitController;
        this.worldController = worldController;
        this.gameController = gameController;
        this.voteController = voteController;
        this.denEconomyAPI = denEconomyAPI;
        this.location = SerializeUtil.deserializeLocation(lobbyDataFile.getString("location"));
        this.items = new LinkedHashMap<>();
        this.onReload(false);
    }

    public void setLocation(Location location) {
        this.location = location;

        this.lobbyDataFile.set("location", SerializeUtil.serializeLocation(location));
        this.lobbyDataFile.save();
    }

    public Location getLocation(Player player) {
        return location == null ? player.getWorld().getSpawnLocation() : location;
    }

    public ActionItem getActionItemByItem(ItemStack itemStack) {
        return items.values().stream()
                .filter(actionItem -> actionItem.isSimilar(itemStack))
                .findFirst()
                .orElse(null);
    }

    public void registerItems(ActionItem... actionItems) {
        for (ActionItem actionItem : actionItems) {
            items.put(actionItem.getId(), actionItem);
        }
    }

    public void giveItems(Player player) {
        PlayerUtil.reset(player, true);

        items.values().forEach(actionItem -> player.getInventory().setItem(actionItem.getSlot(), actionItem.getItemStack()));

        player.updateInventory();
    }

    public void onReload(boolean reload) {
        if (reload) items.clear();

        ConfigurationSection itemsSection = lobbyDataFile.getConfiguration().getConfigurationSection("items");
        if (itemsSection == null) throw new IllegalStateException("Items section is missing in the lobby data file.");

        registerItems(
                new KitActionItem(itemsSection, userController, kitController, denEconomyAPI),
                new VoteActionItem(itemsSection, voteController, worldController, gameController),
                new TeamActionItem(itemsSection, teamController, gameController)
        );

        if (reload) {
            Bukkit.getOnlinePlayers().forEach(this::giveItems);
        }
    }
}
