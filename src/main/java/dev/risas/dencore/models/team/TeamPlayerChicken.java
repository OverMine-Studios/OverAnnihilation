package dev.risas.dencore.models.team;

import dev.risas.dencore.models.game.GameTeam;
import dev.risas.dencore.utilities.BukkitUtil;
import lombok.Getter;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * @author Risas
 * @date 24-05-2025
 * @discord https://risas.me/discord
 */

@Getter
public class TeamPlayerChicken {

    private final UUID uuid;
    private final Player player;
    private final GameTeam gameTeam;
    private final ItemStack[] armorContents, inventoryContents;
    private Chicken chicken;

    public TeamPlayerChicken(
            Player player,
            GameTeam gameTeam,
            ItemStack[] armorContents,
            ItemStack[] inventoryContents) {
        this.player = player;
        this.gameTeam = gameTeam;
        this.uuid = player.getUniqueId();
        this.armorContents = BukkitUtil.getItemStackClones(armorContents);
        this.inventoryContents = BukkitUtil.getItemStackClones(inventoryContents);
    }

    public void spawn() {
         Chicken chicken = player.getWorld().spawn(player.getLocation(), Chicken.class);
         chicken.setCustomName(player.getDisplayName());
         chicken.setCustomNameVisible(true);
         this.chicken = chicken;
    }

    public void despawn() {
        if (chicken != null && !chicken.isDead()) {
            chicken.remove();
        }
    }
}
