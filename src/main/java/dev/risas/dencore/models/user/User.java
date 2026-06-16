package dev.risas.dencore.models.user;

import dev.risas.dencore.controllers.KitController;
import dev.risas.dencore.models.kit.Kit;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter @Setter
public class User {

    private UUID uuid;
    private String name, lowerName;
    private String selectedKit;

    public User(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.lowerName = name.toLowerCase();
        this.selectedKit = "civil";
    }

    public boolean hasSelectedKit() {
        return selectedKit != null;
    }

    public Kit getKit(KitController kitController) {
        return kitController.getKitByName(selectedKit);
    }
}
