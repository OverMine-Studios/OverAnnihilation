package dev.risas.dencore.listeners;

import dev.risas.dencore.controllers.ChatController;
import dev.risas.dencore.controllers.GameController;
import dev.risas.dencore.controllers.LobbyController;
import dev.risas.dencore.controllers.StaffController;
import dev.risas.dencore.models.game.Game;
import dev.risas.dencore.models.game.GameStatus;
import dev.risas.dencore.models.game.GameTeam;
import dev.risas.dencore.models.items.ActionItem;
import dev.risas.dencore.utilities.ChatUtil;
import dev.risas.dencore.utilities.FileConfig;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

public class LobbyListener implements Listener {

    private final FileConfig configFile;
    private final LobbyController lobbyController;
    private final GameController gameController;
    private final ChatController chatController;
    private final StaffController staffController;

    public LobbyListener(
            FileConfig configFile,
            LobbyController lobbyController,
            GameController gameController,
            ChatController chatController,
            StaffController staffController) {
        this.configFile = configFile;
        this.lobbyController = lobbyController;
        this.gameController = gameController;
        this.chatController = chatController;
        this.staffController = staffController;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        event.setCancelled(true);

        String message = event.getMessage();
        boolean isGlobalChat = message.startsWith("!");

        Player player = event.getPlayer();

        Game game = gameController.getGame();
        GameStatus gameStatus = game.getStatus();
        GameTeam gameTeam = gameController.getGameTeam(player.getUniqueId());

        if (isGlobalChat && gameStatus == GameStatus.PLAYING && gameTeam != null) {
            if (!chatController.isGlobalChat() && !player.hasPermission("dencore.chat.bypass")) {
                ChatUtil.sendMessage(player, "&cEl chat global está desactivado en este momento.");
                return;
            }

            message = message.substring(1).trim();

            ChatUtil.sendBroadcast(configFile.getString("chat-system.global")
                    .replace("%team-tag%", gameTeam.getTeam().getTag())
                    .replace("%player%", player.getName())
                    .replace("%message%", message));
        }
        else if (gameStatus == GameStatus.PLAYING && gameTeam != null) {
            if (!chatController.isTeamChat() && !player.hasPermission("dencore.chat.bypass")) {
                ChatUtil.sendMessage(player, "&cEl chat de equipo esta desactivado en este momento.");
                return;
            }

            gameTeam.sendMessage(configFile.getString("chat-system.team")
                    .replace("%player%", player.getName())
                    .replace("%message%", message));
            staffController.sendSpyMessage(configFile.getString("chat-system.spy")
                    .replace("%team-tag%", gameTeam.getTeam().getTag())
                    .replace("%player%", player.getName())
                    .replace("%message%", message));
        }
        else {
            if (!chatController.isGlobalChat() && !player.hasPermission("dencore.chat.bypass")) {
                ChatUtil.sendMessage(player, "&cEl chat global está desactivado en este momento.");
                return;
            }

            ChatUtil.sendBroadcast(configFile.getString("chat-system.lobby")
                    .replace("%player%", player.getName())
                    .replace("%message%", message));
        }
    }

    @EventHandler
    public void onActionItemInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;

        ItemStack itemStack = event.getItem();
        if (itemStack == null || itemStack.getType() == Material.AIR) return;

        ActionItem actionItem = lobbyController.getActionItemByItem(itemStack);
        if (actionItem == null) return;

        event.setCancelled(true);

        Player player = event.getPlayer();
        player.updateInventory();

        actionItem.onAction(player);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (gameController.hasTeam(player.getUniqueId())) return;

        player.teleport(lobbyController.getLocation(player));

        lobbyController.giveItems(player);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (gameController.hasTeam(player.getUniqueId())
                || player.getGameMode() == GameMode.CREATIVE) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (gameController.hasTeam(player.getUniqueId())
                || player.getGameMode() == GameMode.CREATIVE) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        if (gameController.hasTeam(player.getUniqueId())
                || player.getGameMode() == GameMode.CREATIVE) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        if (gameController.hasTeam(player.getUniqueId())
                || player.getGameMode() == GameMode.CREATIVE) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        if (gameController.hasTeam(event.getEntity().getUniqueId())) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntity();
        if (gameController.hasTeam(player.getUniqueId())) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onDamageEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;

        Player player = (Player) event.getDamager();
        if (gameController.hasTeam(player.getUniqueId())) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onMonsterSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        if (event.toWeatherState()) {
            event.setCancelled(true);
        }
    }
}
