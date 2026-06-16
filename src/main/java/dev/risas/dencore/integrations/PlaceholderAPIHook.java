package dev.risas.dencore.integrations;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.controllers.GameController;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

@UtilityClass
public class PlaceholderAPIHook {

    public boolean enabled;

    public void initialize(DenCore plugin, GameController gameController) {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            DenCoreExpansion papi = new DenCoreExpansion(plugin, gameController);
            if (!papi.isRegistered()) papi.register();

            enabled = true;
        }
    }
}
