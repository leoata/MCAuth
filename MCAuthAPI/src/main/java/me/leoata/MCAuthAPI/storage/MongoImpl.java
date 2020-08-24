package me.leoata.MCAuthAPI.storage;

import com.mongodb.*;
import lombok.Getter;
import me.leoata.MCAuthAPI.McAuthApiApplication;

import java.util.Collections;

public class MongoImpl {

    @Getter private MongoClient client;
    private final static String ip = "";
    private final static int port = 27017;
    private final static String username = "";
    private final static String database = "";
    private final static String password = "";

    public MongoImpl(){
        connect();
    }

    private void connect(){
        MongoCredential credential = MongoCredential.createCredential(MongoImpl.username, MongoImpl.database, MongoImpl.password.toCharArray());

        client = new MongoClient(new ServerAddress(MongoImpl.ip, MongoImpl.port), Collections.singletonList(credential));
        McAuthApiApplication.getInstance().getLogger().info("Connected to the MongoDB database.");
        DB db = client.getDB("mcauthdb");
        db.createCollection("Servers", null);
        db.createCollection("Users", null);
    }
}
