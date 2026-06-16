package dev.risas.dencore.utilities;

import dev.risas.dencore.DenCore;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

@UtilityClass
public class TaskUtil {
    
    private final DenCore plugin = DenCore.getInstance();
    
    public void run(Runnable runnable) {
        Bukkit.getServer().getScheduler().runTask(plugin, runnable);
    }

    public void runTimer(Runnable runnable, long delay, long timer) {
        Bukkit.getServer().getScheduler().runTaskTimer(plugin, runnable, delay, timer);
    }

    public void runTimer(BukkitRunnable runnable, long delay, long timer) {
        runnable.runTaskTimer(plugin, delay, timer);
    }

    public void runTimerAsync(Runnable runnable, long delay, long timer) {
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(plugin, runnable, delay, timer);
    }

    public void runTimerAsync(BukkitRunnable runnable, long delay, long timer) {
        runnable.runTaskTimerAsynchronously(plugin, delay, timer);
    }

    public void runLater(Runnable runnable, long delay) {
        Bukkit.getServer().getScheduler().runTaskLater(plugin, runnable, delay);
    }

    public void runLaterAsync(Runnable runnable, long delay) {
        Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay);
    }

    public void runTaskTimer(Runnable runnable, long delay, long timer) {
        Bukkit.getServer().getScheduler().runTaskTimer(plugin, runnable, 20L * delay, 20L * timer);
    }

    public void runTaskTimerAsynchronously(Runnable runnable, int delay) {
        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(plugin, runnable, 20L * delay, 20L * delay);
    }

    public void runAsync(Runnable runnable) {
        Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    public void scheduleSyncDelayedTask(Runnable runnable) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, runnable);
    }

    public void scheduleSyncDelayedTask(Runnable runnable, long delay) {
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, runnable, delay);
    }

    public void cancelTask(int id) {
        Bukkit.getServer().getScheduler().cancelTask(id);
    }
}