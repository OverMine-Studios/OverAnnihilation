package dev.risas.dencore.listeners;

import dev.risas.dencore.controllers.*;
import dev.risas.dencore.models.game.Game;
import dev.risas.dencore.models.game.GameStatus;
import dev.risas.dencore.models.game.GameTeam;
import dev.risas.dencore.models.kit.Kit;
import dev.risas.dencore.models.kit.ability.KitAbility;
import dev.risas.dencore.models.kit.ability.types.BerserkerAbility;
import dev.risas.dencore.models.kit.ability.types.ReparadorAbility;
import dev.risas.dencore.models.nexus.Nexus;
import dev.risas.dencore.models.team.Team;
import dev.risas.dencore.models.team.TeamPlayer;
import dev.risas.dencore.models.team.TeamPlayerChicken;
import dev.risas.dencore.models.team.TeamPlayerStatus;
import dev.risas.dencore.models.user.User;
import dev.risas.dencore.utilities.ChatUtil;
import dev.risas.dencore.utilities.PlayerUtil;
import dev.risas.dencore.utilities.TaskUtil;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameListener implements Listener {

    private final GameController gameController;
    private final UserController userController;
    private final KitController kitController;
    private final LobbyController lobbyController;
    private final ToolsController toolsController;

    public GameListener(
            GameController gameController,
            UserController userController,
            KitController kitController,
            LobbyController lobbyController,
            ToolsController toolsController) {
        this.gameController = gameController;
        this.userController = userController;
        this.kitController = kitController;
        this.lobbyController = lobbyController;
        this.toolsController = toolsController;
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onTeamAreaBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (gameController.canInteractInTeamArea(player, event.getBlock().getLocation())) return;

        event.setCancelled(true);
        ChatUtil.sendMessage(player, "&cNo puedes romper en un área protegida.");
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onTeamAreaBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (gameController.canInteractInTeamArea(player, event.getBlock().getLocation())) return;

        event.setCancelled(true);
        ChatUtil.sendMessage(player, "&cNo puedes construir en un área protegida.");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (gameController.getGame().getStatus() != GameStatus.PLAYING) return;

        Player player = event.getPlayer();
        Material blockType = event.getBlock().getType();

        List<String> tools = toolsController.getTools(blockType);
        if (tools == null || toolsController.isValidTool(tools, player.getItemInHand())) return;

        event.setCancelled(true);
        ChatUtil.sendMessage(player, new String[]{
                "&cEste bloque solo se puede romper",
                "&ccon las siguientes herramientas: &e" + toolsController.getToolsFormatted(tools)
        });
    }

    @EventHandler
    public void onNexusDamage(BlockBreakEvent event) {
        Game game = gameController.getGame();
        if (game.getStatus() != GameStatus.PLAYING) return;

        Block block = event.getBlock();
        if (block.getType() != Material.ENDER_STONE) return;

        Nexus nexus = gameController.getNexusByLocation(block.getLocation());
        if (nexus == null) return;

        Player player = event.getPlayer();
        GameTeam gameTeam = gameController.getGameTeam(player.getUniqueId());
        if (gameTeam == null) return;

        event.setCancelled(true);

        if (nexus.equals(gameTeam.getNexus())) {
            PlayerUtil.sendSound(player, Sound.VILLAGER_NO);
            ChatUtil.sendMessage(player, "&cNo puedes dañar tu propio nexo.");
            return;
        }

        KitAbility<? extends Kit> kitAbility = userController.getUser(player.getUniqueId())
                .getKit(kitController)
                .getAbility();

        if (kitAbility instanceof ReparadorAbility) {
            Map<String, Object> data = new HashMap<>();
            data.put("phaseType", game.getPhase().getType());
            data.put("nexus", gameTeam.getNexus());

            ReparadorAbility reparadorAbility = (ReparadorAbility) kitAbility;
            reparadorAbility.call(player, data);
        }

        nexus.damage(1);

        GameTeam opponentGameTeam = gameController.getGameTeamByNexus(nexus);
        Team opponentTeam = opponentGameTeam.getTeam();

        if (nexus.isDestroyed()) {
            block.setType(Material.BEDROCK);
            opponentGameTeam.sendSound(Sound.ENDERDRAGON_DEATH);
            opponentGameTeam.sendMessage("&cTu nexo ha sido destruido por &6" + player.getName() + "&c.");

            ChatUtil.sendBroadcast("&fEl nexo del equipo " + opponentTeam.getNameColored() + " &fha sido destruido por &6" + player.getName() + "&f.");
            return;
        }

        opponentGameTeam.sendSound(Sound.ANVIL_LAND);
        opponentGameTeam.sendMessage("&6" + player.getName() + " &eha dañado tu nexo.");

        ChatUtil.sendMessage(player, "&fHas dañado el nexo del equipo " + opponentTeam.getNameColored());
    }

    @EventHandler
    public void onTeamPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (!gameController.hasTeam(player.getUniqueId())) return;

        GameTeam gameTeam = gameController.getGameTeam(player.getUniqueId());
        TeamPlayer teamPlayer = gameTeam.getTeamPlayer(player.getUniqueId());

        gameController.getGame().addChicken(player, gameTeam);

        gameTeam.sendMessage("&cEl miembro de tu equipo " + player.getName() + " se ha desconectado de la partida.");
        gameTeam.removeTeamPlayer(teamPlayer, player, gameController);
    }

    @EventHandler
    public void onChickenDeath(EntityDeathEvent event) {
        Game game = gameController.getGame();

        if (game.getStatus() != GameStatus.PLAYING
                || !(event.getEntity() instanceof Chicken)) return;

        Chicken chicken = (Chicken) event.getEntity();
        TeamPlayerChicken teamPlayerChicken = game.getChickenByEntity(chicken);
        if (teamPlayerChicken == null) return;

        event.setDroppedExp(0);
        event.getDrops().clear();

        for (ItemStack itemStack : teamPlayerChicken.getArmorContents()) {
            if (itemStack == null) continue;
            event.getDrops().add(itemStack);
        }
        for (ItemStack itemStack : teamPlayerChicken.getInventoryContents()) {
            if (itemStack == null) continue;
            event.getDrops().add(itemStack);
        }

        game.removeChicken(teamPlayerChicken);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onTeamPlayerKill(PlayerDeathEvent event) {
        Player player = event.getEntity(), killer = player.getKiller();

        GameTeam gameTeamPlayer = gameController.getGameTeam(player.getUniqueId());
        if (gameTeamPlayer == null) return;

        User playerUser = userController.getUser(player.getUniqueId());
        Team playerTeam = gameTeamPlayer.getTeam();

        if (killer == null) {
            event.setDeathMessage(ChatUtil.translate(
                    playerTeam.getBukkitColor() + player.getName() + "&7(" + playerUser.getSelectedKit() + ") " + "&eha muerto."
            ));
            return;
        }

        GameTeam killerMatchTeam = gameController.getGameTeam(killer.getUniqueId());
        if (killerMatchTeam == null) return;

        User killerUser = userController.getUser(killer.getUniqueId());
        Team killerTeam = killerMatchTeam.getTeam();

        event.setDeathMessage(ChatUtil.translate(
                playerTeam.getBukkitColor() + player.getName() + "&7(" + playerUser.getSelectedKit() + ") " + "&efue asesinado por " +
                        killerTeam.getBukkitColor() + killer.getName() + "&7(" + killerUser.getSelectedKit() + ")"
        ));

        TeamPlayer killerTeamPlayer = killerMatchTeam.getTeamPlayer(killer.getUniqueId());
        killerTeamPlayer.addKill();

        KitAbility<? extends Kit> kitAbility = killerUser.getKit(kitController)
                .getAbility();

        if (kitAbility instanceof BerserkerAbility) {
            BerserkerAbility berserkerAbility = (BerserkerAbility) kitAbility;
            berserkerAbility.call(killer);
        }
    }

    @EventHandler
    public void onTeamPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        GameTeam gameTeam = gameController.getGameTeam(player.getUniqueId());
        if (gameTeam == null) return;

        TaskUtil.run(() -> player.spigot().respawn());

        Nexus nexus = gameTeam.getNexus();
        if (!nexus.isDestroyed()) return;

        TeamPlayer teamPlayer = gameTeam.getTeamPlayer(player.getUniqueId());
        teamPlayer.setStatus(TeamPlayerStatus.DEATH);
    }

    @EventHandler
    public void onTeamPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        GameTeam gameTeam = gameController.getGameTeam(player.getUniqueId());
        if (gameTeam == null) return;

        TeamPlayer teamPlayer = gameTeam.getTeamPlayer(player.getUniqueId());
        if (teamPlayer == null) return;

        if (teamPlayer.getStatus() == TeamPlayerStatus.DEATH) {
            event.setRespawnLocation(lobbyController.getLocation(player));
            gameTeam.removeTeamPlayer(teamPlayer, player, gameController);
            return;
        }

        Game game = gameController.getGame();
        Team team = gameTeam.getTeam();

        event.setRespawnLocation(game.getWorld().getSpawnLocation(team.getType()));

        TaskUtil.runLater(() ->
                game.respawnPlayer(player, userController.getUser(player.getUniqueId()), team, kitController), 1L);
    }
}
