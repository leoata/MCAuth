package me.leoata.MCAuthAPI.storage;

import com.mongodb.*;
import lombok.Getter;
import me.leoata.MCAuthAPI.McAuthApiApplication;

import javax.swing.text.Document;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

public class MongoImpl {

    @Getter private MongoClient client;

    public MongoImpl(){
        connect();
    }

    private void connect(){
        MongoCredential credential = MongoCredential.createCredential("mcauth", "admin", "g3kPJnTtf8pq5YaxpLAYwU488ZSftcHvrzpFppft".toCharArray());
        //MongoCredential credential = MongoCredential.createCredential("adminuser", "admin", "sZeQ4n4QuMFbCpatY4f82QnGr2WSVpcg4Qpr2E9D".toCharArray());

        client = new MongoClient(new ServerAddress("174.138.37.168", 27017), Collections.singletonList(credential));
        McAuthApiApplication.getInstance().getLogger().info("Connected to the MongoDB database.");
        DB db = client.getDB("mcauthdb");
        db.createCollection("Servers", null);
        db.createCollection("Users", null);
    }
}
