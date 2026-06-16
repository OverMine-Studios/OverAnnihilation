package dev.risas.dencore;

import dev.risas.dencore.commands.DenCoreCommand;
import dev.risas.dencore.commands.DenCoreHelpCommand;
import dev.risas.dencore.commands.DenCoreReloadCommand;
import dev.risas.dencore.commands.chat.ChatCommand;
import dev.risas.dencore.commands.chat.ChatGlobalCommand;
import dev.risas.dencore.commands.chat.ChatSpyCommand;
import dev.risas.dencore.commands.chat.ChatTeamCommand;
import dev.risas.dencore.commands.game.GameCommand;
import dev.risas.dencore.commands.game.GameStartCommand;
import dev.risas.dencore.commands.kit.KitCommand;
import dev.risas.dencore.controllers.*;
import dev.risas.dencore.controllers.mongo.MongoController;
import dev.risas.dencore.integrations.PlaceholderAPIHook;
import dev.risas.dencore.listeners.*;
import dev.risas.dencore.models.scoreboard.ScoreboardProvider;
import dev.risas.dencore.utilities.FileConfig;
import dev.risas.deneconomy.DenEconomyAPI;
import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class DenCore extends JavaPlugin {

    private FileConfig
            configFile,
            kitsFile,
            scoreboardFile,
            lobbyDataFile;

    private DenEconomyAPI denEconomyAPI;

    private CommandController commandController;
    private MongoController mongoController;
    private WorldController worldController;
    private UserController userController;
    private ScoreboardController scoreboardController;
    private GameController gameController;
    private TeamController teamController;
    private LobbyController lobbyController;
    private KitController kitController;
    private ConfigurationItemController configurationItemController;
    private OreController oreController;
    private VoteController voteController;
    private StaffController staffController;
    private ChatController chatController;

    @Override
    public void onEnable() {
        this.configFile = new FileConfig(this, "config.yml");
        this.kitsFile = new FileConfig(this, "kits.yml");
        this.scoreboardFile = new FileConfig(this, "scoreboard.yml");
        this.lobbyDataFile = new FileConfig(this, "data/lobby-data.yml");

        this.denEconomyAPI = new DenEconomyAPI();

        this.commandController = new CommandController(this);
        this.mongoController = new MongoController(this);
        this.worldController = new WorldController(this);
        this.userController = new UserController(this, mongoController);
        this.kitController = new KitController(this, userController);
        this.teamController = new TeamController();
        this.voteController = new VoteController(worldController);
        this.gameController = new GameController(this, configFile, teamController, userController, kitController, voteController, worldController);
        this.lobbyController = new LobbyController(lobbyDataFile, teamController, userController, kitController, worldController, gameController, voteController, denEconomyAPI);
        this.configurationItemController = new ConfigurationItemController(this, worldController, teamController, lobbyController);
        this.oreController = new OreController(this);
        this.staffController = new StaffController(configFile);
        this.chatController = new ChatController();

        ToolsController toolsController = new ToolsController(configFile);

        this.scoreboardController = new ScoreboardController(scoreboardFile);
        this.scoreboardController.setAdapter(new ScoreboardProvider(
                configFile,
                scoreboardController,
                userController,
                gameController,
                kitController
        ));

        this.registerListeners(
                new UserListener(this),
                new ScoreboardListener(this),
                new MenuListener(),
                new SoulboundListener(),
                new LobbyListener(configFile, lobbyController, gameController, chatController, staffController),
                new KitListener(userController, kitController),
                new ConfigurationItemListener(configurationItemController),
                new GameListener(gameController, userController, kitController, lobbyController, toolsController),
                new VoteListener(configFile, voteController, gameController),
                new StaffListener(staffController)
        );
        this.commandController.registerCommands(
                new DenCoreCommand(configurationItemController),
                new DenCoreHelpCommand(),
                new DenCoreReloadCommand(this),
                new KitCommand(userController, kitController, denEconomyAPI),
                new GameCommand(),
                new GameStartCommand(gameController, worldController),
                new ChatCommand(),
                new ChatGlobalCommand(chatController),
                new ChatTeamCommand(chatController),
                new ChatSpyCommand(staffController)
        );

        PlaceholderAPIHook.initialize(this, gameController);
    }

    public void registerListeners(Listener... listeners) {
        PluginManager pluginManager = this.getServer().getPluginManager();

        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, this);
        }
    }

    @Override
    public void onDisable() {
        this.scoreboardController.onDisable();
    }

    public void onReload() {
        this.configFile.reload();
        this.scoreboardFile.reload();
        this.lobbyDataFile.reload();
        this.scoreboardController.onReload();
        this.lobbyController.onReload(true);
    }

    public static DenCore getInstance() {
        return getPlugin(DenCore.class);
    }
}
