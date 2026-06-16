package dev.risas.dencore.controllers.mongo;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dev.risas.dencore.DenCore;
import dev.risas.dencore.utilities.FileConfig;
import lombok.Getter;
import org.bson.Document;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
public class MongoController {

    private final FileConfig configFile;

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> users, clans;
    private boolean connected;

    public MongoController(DenCore plugin) {
        this.configFile = plugin.getConfigFile();

        System.setProperty("DEBUG.GO", "true");
        System.setProperty("DB.TRACE", "true");
        Logger.getLogger("org.mongodb.driver")
                .setLevel(Level.WARNING);

        try {
            this.mongoClient = MongoClients.create(configFile.getString("mongo.uri"));
            this.database = mongoClient.getDatabase(configFile.getString("mongo.database"));
            this.connected = true;
            this.loadCollections();

            plugin.getLogger().info("MongoDB se ha conectado correctamente.");
        }
        catch (MongoException ex) {
            plugin.getLogger().severe("MongoDB no se ha podido conectar.");
        }
    }

    private void createCollection(String name) {
        if (!database.listCollectionNames().into(new ArrayList<>()).contains(name)) {
            database.createCollection(name);
        }
    }

    private void loadCollections() {
        createCollection("users");
        createCollection("clans");

        users = database.getCollection("users");
        clans = database.getCollection("clans");
    }
}
