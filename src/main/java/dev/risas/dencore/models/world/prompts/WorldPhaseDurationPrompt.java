package dev.risas.dencore.models.world.prompts;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.controllers.ConfigurationItemController;
import dev.risas.dencore.controllers.LobbyController;
import dev.risas.dencore.controllers.TeamController;
import dev.risas.dencore.controllers.WorldController;
import dev.risas.dencore.models.world.World;
import dev.risas.dencore.ui.world.WorldEditorMenu;
import dev.risas.dencore.utilities.ChatUtil;
import dev.risas.dencore.utilities.TaskUtil;
import dev.risas.dencore.utilities.TimeUtil;
import org.bukkit.Sound;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author Risas
 * @date 21-05-2025
 * @discord https://risas.me/discord
 */
public class WorldPhaseDurationPrompt extends StringPrompt {

    private final DenCore plugin;
    private final World world;
    private final WorldController worldController;
    private final TeamController teamController;
    private final ConfigurationItemController configurationItemController;
    private final LobbyController lobbyController;

    public WorldPhaseDurationPrompt(
            DenCore plugin,
            World world,
            WorldController worldController,
            TeamController teamController,
            ConfigurationItemController configurationItemController,
            LobbyController lobbyController) {
        this.plugin = plugin;
        this.world = world;
        this.worldController = worldController;
        this.teamController = teamController;
        this.configurationItemController = configurationItemController;
        this.lobbyController = lobbyController;
    }

    @Override
    public @NotNull String getPromptText(@NotNull ConversationContext context) {
        return ChatUtil.translate("&eEscribe la duración de la fase o &ccancel &epara cancelar.");
    }

    @Override
    public Prompt acceptInput(ConversationContext context, String input) {
        Conversable conversable = context.getForWhom();
        Player player = (Player) conversable;

        if (input.equalsIgnoreCase("cancel")) {
            TaskUtil.runLater(() -> new WorldEditorMenu(
                    player,
                    plugin,
                    world,
                    worldController,
                    teamController,
                    configurationItemController,
                    lobbyController
            ).open(), 1L);

            player.playSound(player.getLocation(), Sound.VILLAGER_NO, 1.0F, 1.0F);
            ChatUtil.sendMessageRaw(player, "&cHas cancelado la acción.");
            return END_OF_CONVERSATION;
        }

        int duration = TimeUtil.formatInt(input);

        if (duration == -1 || duration == 0) {
            ChatUtil.sendMessage(player, "&cLa duración debe ser un número mayor a 0.");
            return this;
        }

        world.getPhases().forEach(phase -> phase.setDuration(duration));
        worldController.saveWorld(world);

        TaskUtil.runLater(() -> new WorldEditorMenu(
                player,
                plugin,
                world,
                worldController,
                teamController,
                configurationItemController,
                lobbyController
        ).open(), 1L);

        ChatUtil.sendMessageRaw(player, "&eLa duración de las fases ha sido establecida a &f" + input + "&e.");
        return END_OF_CONVERSATION;
    }
}
