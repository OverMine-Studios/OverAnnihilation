package dev.risas.dencore.controllers;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.tasks.OreRegenerationTask;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.block.BlockState;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Risas
 * @date 20-05-2025
 * @discord https://risas.me/discord
 */

@Getter
public class OreController {

    private final DenCore plugin;
    private final Map<BlockState, Long> blockStateMap;
    private OreRegenerationTask task;

    public OreController(DenCore plugin) {
        this.plugin = plugin;
        this.blockStateMap = new HashMap<>();
    }

    public boolean isRegenBlock(Location location) {
        return blockStateMap.keySet().stream().anyMatch(blockState -> blockState.getLocation().equals(location));
    }

    public void startOreRegenerationTask() {
        if (task == null) {
            task = new OreRegenerationTask(plugin, this);
            task.start();
        }
    }

    public void stopOreRegenerationTask() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    public void addBlockState(BlockState blockState, long delay) {
        blockStateMap.put(blockState, System.currentTimeMillis() + delay);
        startOreRegenerationTask();
    }

    public long getBlockStateRemainingTime(BlockState blockState) {
        return blockStateMap.get(blockState) - System.currentTimeMillis();
    }

    public void restoreBlockState(BlockState blockState) {
        blockState.update(true);
    }

    public void onDisable() {
        this.blockStateMap.keySet().forEach(this::restoreBlockState);
    }
}
