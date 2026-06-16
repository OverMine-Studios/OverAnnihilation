package dev.risas.dencore.models.kit.ability.types;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.models.game.GameTeam;
import dev.risas.dencore.models.kit.ability.KitAbility;
import dev.risas.dencore.models.kit.types.DefensorKit;
import dev.risas.dencore.models.nexus.Nexus;
import dev.risas.dencore.utilities.ChatUtil;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class DefensorAbility extends KitAbility<DefensorKit> {

    public DefensorAbility(
            DefensorKit kit,
            DenCore plugin,
            ConfigurationSection section) {
        super(kit, plugin, section);
    }

    @Override
    public void call(Player player) {
        GameTeam gameTeam = DenCore.getInstance().getGameController().getGameTeam(player.getUniqueId());
        if (gameTeam == null) return;

        Nexus nexus = gameTeam.getNexus();
        if (nexus == null) return;

        Location playerLocation = player.getLocation();
        Location nexusLocation = nexus.getLocation().clone();
        double distance = nexusLocation.distance(playerLocation);

        int radius = Integer.parseInt(getKit().getSettings().get("radius"));

        if (radius < distance) {
            ChatUtil.sendMessage(player, "&cNo puedes usar esta habilidad tan lejos de tu nexo!");
            return;
        }

        player.teleport(nexusLocation);

        super.call(player);
    }
}
