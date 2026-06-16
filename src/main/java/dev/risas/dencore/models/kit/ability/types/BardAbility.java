package dev.risas.dencore.models.kit.ability.types;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.models.kit.ability.KitAbility;
import dev.risas.dencore.models.kit.ability.types.bard.BardAbilityTask;
import dev.risas.dencore.models.kit.types.BardKit;
import dev.risas.dencore.utilities.ChatUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class BardAbility extends KitAbility<BardKit> {

    private final DenCore plugin;

    public BardAbility(
            BardKit kit,
            DenCore plugin,
            ConfigurationSection section) {
        super(kit, plugin, section);
        this.plugin = plugin;
    }

    @Override
    public boolean isBuildable() {
        return true;
    }

    @Override
    public void call(Player player, Location blockLocation) {
        if (!canBuild(blockLocation)) {
            ChatUtil.sendMessage(player, "&cNo puedes construir el soporte aquí.");
            return;
        }

        BardAbilityTask bardAbilityTask = new BardAbilityTask(
                plugin,
                this,
                player,
                blockLocation
        );
        bardAbilityTask.start();

        super.call(player, blockLocation);
    }

    private boolean canBuild(Location location) {
        World world = location.getWorld();

        for (int i = 1; i <= 2; i++) {
            Location checkLoc = location.clone().add(0, i, 0);
            Material type = world.getBlockAt(checkLoc).getType();

            if (type != Material.AIR) return false;
        }

        return true;
    }

    public void build(Location location) {
        World world = location.getWorld();

        for (int i = 0; i <= 2; i++) {
            Material material = (i == 2) ? Material.JUKEBOX : Material.FENCE;
            world.getBlockAt(location.clone().add(0, i, 0)).setType(material);
        }
    }

    public void unBuild(Location location) {
        World world = location.getWorld();

        for (int i = 0; i <= 2; i++) {
            world.getBlockAt(location.clone().add(0, i, 0)).setType(Material.AIR);
        }
    }
}
