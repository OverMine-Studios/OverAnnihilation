package dev.risas.dencore.controllers;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import dev.risas.dencore.DenCore;
import dev.risas.dencore.controllers.mongo.MongoController;
import dev.risas.dencore.models.user.IUser;
import dev.risas.dencore.models.user.User;
import dev.risas.dencore.models.user.storage.UserMongo;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Getter
public class UserController {

    private final DenCore plugin;
    private final IUser user;
    private final Map<UUID, User> users;
    private final Cache<String, User> nameUserCache;
    private final Cache<UUID, User> uuidUserCache;

    public UserController(DenCore plugin, MongoController mongoController) {
        this.plugin = plugin;
        this.users = new ConcurrentHashMap<>();
        this.nameUserCache = CacheBuilder.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .build();
        this.uuidUserCache = CacheBuilder.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .build();
        this.user = new UserMongo(this, mongoController);
    }

    public User getUser(UUID uuid) {
        return users.get(uuid);
    }

    public User getUserUUIDFromDatabase(UUID uuid, boolean load) {
        User user = users.get(uuid);

        if (user != null) {
            if (load) loadUser(user);
            return user;
        }

        User cachedUser = uuidUserCache.getIfPresent(uuid);

        if (cachedUser != null) {
            if (load) loadUser(cachedUser);
            return cachedUser;
        }

        return this.user.getUser(uuid, load);
    }

    public User getUserNameFromDatabase(String name, boolean load) {
        String key = name.toLowerCase();
        Player player = plugin.getServer().getPlayer(key);

        if (player != null) {
            User user = getUser(player.getUniqueId());
            if (user != null && load) loadUser(user);

            return user;
        }

        User cachedUser = nameUserCache.getIfPresent(key);

        if (cachedUser != null) {
            if (load) loadUser(cachedUser);
            return cachedUser;
        }

        return this.user.getUser(key, load);
    }

    public User createUser(UUID uuid, String name) {
        User user = this.user.createUser(uuid, name);
        users.put(uuid, user);
        return user;
    }

    public void saveUser(User user) {
        CompletableFuture.runAsync(() -> this.user.saveUser(user));
    }

    public void loadUser(User user) {
        this.user.loadUser(user);
    }

    public void destroyUser(User user) {
        users.remove(user.getUuid());
        nameUserCache.invalidate(user.getLowerName());
        uuidUserCache.invalidate(user.getUuid());
    }
}
