package dev.risas.dencore.tasks;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.controllers.OreController;
import org.bukkit.block.BlockState;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;
import java.util.Map;

public class OreRegenerationTask extends BukkitRunnable {

    private final DenCore plugin;
    private final OreController oreController;

    public OreRegenerationTask(
            DenCore plugin,
            OreController oreController) {
        this.plugin = plugin;
        this.oreController = oreController;
    }

    @Override
    public void run() {
        Map<BlockState, Long> blockStateMap = oreController.getBlockStateMap();

        if (blockStateMap.isEmpty()) {
            oreController.stopOreRegenerationTask();
            return;
        }

        Iterator<BlockState> blockStateIterator = blockStateMap.keySet().iterator();

        while (blockStateIterator.hasNext()) {
            BlockState blockState = blockStateIterator.next();
            long time = oreController.getBlockStateRemainingTime(blockState);

            if (time <= 0) {
                oreController.restoreBlockState(blockState);
                blockStateIterator.remove();
            }
        }
    }

    public void start() {
        this.runTaskTimer(plugin, 20L, 20L);
    }
}
