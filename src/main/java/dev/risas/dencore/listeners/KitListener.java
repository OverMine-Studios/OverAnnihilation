package dev.risas.dencore.listeners;

import dev.risas.dencore.controllers.KitController;
import dev.risas.dencore.controllers.UserController;
import dev.risas.dencore.models.kit.Kit;
import dev.risas.dencore.models.kit.ability.KitAbility;
import dev.risas.dencore.models.kit.types.GuerreroKit;
import dev.risas.dencore.models.user.User;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class KitListener implements Listener {

    private final UserController userController;
    private final KitController kitController;

    public KitListener(UserController userController, KitController kitController) {
        this.userController = userController;
        this.kitController = kitController;
    }

    @EventHandler
    public void onKitAbilityInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (!action.equals(Action.RIGHT_CLICK_AIR) && !action.equals(Action.RIGHT_CLICK_BLOCK)) return;

        KitAbility<? extends Kit> ability = kitController.getKitAbilityIfApplicable(event.getItem());
        if (ability == null || ability.isBuildable() || ability.isFishable()) return;

        event.setCancelled(ability.isCancelable());

        Player player = event.getPlayer();
        player.updateInventory();

        if (ability.isRestricted(player)) return;

        ability.call(player);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onKitAbilityPlace(BlockPlaceEvent event) {
        KitAbility<? extends Kit> ability = kitController.getKitAbilityIfApplicable(event.getItemInHand());
        if (ability == null) return;

        event.setCancelled(ability.isCancelable());

        Player player = event.getPlayer();

        if (ability.isRestricted(player)) return;
        ability.call(player, event.getBlock().getLocation());
    }

    @EventHandler
    public void onKitAbilityFish(PlayerFishEvent event) {
        if (event.getState() == PlayerFishEvent.State.FISHING) return;

        Player player = event.getPlayer();
        KitAbility<? extends Kit> ability = kitController.getKitAbilityIfApplicable(player.getItemInHand());
        if (ability == null) return;

        event.setCancelled(ability.isCancelable());

        if (ability.isRestricted(player)) return;
        ability.call(player, event.getHook());
    }

    @EventHandler
    public void onGuerreroKit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) return;

        Player player = (Player) event.getDamager();
        ItemStack item = player.getInventory().getItemInHand();
        if (item == null || item.getType() == Material.AIR || !item.getType().name().endsWith("_SWORD")) return;

        User user = userController.getUser(player.getUniqueId());
        if (user == null) return;

        Kit kit = user.getKit(kitController);
        if (!(kit instanceof GuerreroKit)) return;

        double kitDamage = 1 + Double.parseDouble(kit.getSettings().get("damage"));
        double originalDamage = event.getDamage();
        double newDamage = originalDamage * kitDamage;

        event.setDamage(newDamage);
    }
}
