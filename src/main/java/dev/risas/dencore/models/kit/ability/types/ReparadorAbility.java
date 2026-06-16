package dev.risas.dencore.models.kit.ability.types;

import dev.risas.dencore.models.kit.ability.KitAbility;
import dev.risas.dencore.models.kit.types.ReparadorKit;
import dev.risas.dencore.models.nexus.Nexus;
import dev.risas.dencore.models.phase.PhaseType;
import dev.risas.dencore.utilities.ChatUtil;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Risas
 * @date 17-04-2025
 * @discord https://risas.me/discord
 */
public class ReparadorAbility extends KitAbility<ReparadorKit> {

    private final Map<PhaseType, Integer> repairPercentages;

    public ReparadorAbility(ReparadorKit kit) {
        super(kit);

        this.repairPercentages = new HashMap<>();
        this.repairPercentages.put(PhaseType.PHASE_I, 10);
        this.repairPercentages.put(PhaseType.PHASE_II, 15);
        this.repairPercentages.put(PhaseType.PHASE_III, 20);
        this.repairPercentages.put(PhaseType.PHASE_IV, 25);
        this.repairPercentages.put(PhaseType.PHASE_V, 30);
        this.repairPercentages.put(PhaseType.PHASE_VI, 35);
    }

    @Override
    public void call(Player player, Map<String, Object> data) {
        PhaseType phaseType = (PhaseType) data.get("phaseType");
        int repairPercentage = repairPercentages.getOrDefault(phaseType, -1);
        if (repairPercentage == -1) return;

        int randomNumber = ThreadLocalRandom.current().nextInt(0, 100);
        if (randomNumber > repairPercentage) return;

        Nexus nexus = (Nexus) data.get("nexus");
        nexus.addHealth(1);

        ChatUtil.sendMessage(player, "&eTu nexo se ha reparado.");
    }
}
