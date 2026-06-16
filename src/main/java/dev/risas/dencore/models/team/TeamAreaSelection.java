package dev.risas.dencore.models.team;

import dev.risas.dencore.DenCore;
import dev.risas.dencore.utilities.cuboid.Cuboid;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

@Setter
public class TeamAreaSelection {

    private Location location1, location2;
    private static final String SELECTION_METADATA_KEY = "team-area-selection";

    public static TeamAreaSelection createOrGetSelection(DenCore plugin, Player player) {
        if (player.hasMetadata(SELECTION_METADATA_KEY)) {
            return (TeamAreaSelection) player.getMetadata(SELECTION_METADATA_KEY).get(0).value();
        }

        TeamAreaSelection selection = new TeamAreaSelection();
        player.setMetadata(SELECTION_METADATA_KEY, new FixedMetadataValue(plugin, selection));
        return selection;
    }

    public Cuboid getCuboid() {
        return new Cuboid(location1, location2);
    }

    public boolean isFullSelected() {
        return location1 != null && location2 != null;
    }

    public void clear(DenCore plugin, Player player) {
        location1 = null;
        location2 = null;
        player.removeMetadata(SELECTION_METADATA_KEY, plugin);
    }
}
