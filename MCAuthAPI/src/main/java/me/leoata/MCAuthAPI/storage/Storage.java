package me.leoata.MCAuthAPI.storage;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import me.leoata.MCAuthAPI.McAuthApiApplication;
import me.leoata.MCAuthAPI.types.Server;
import me.leoata.MCAuthAPI.types.User;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.print.Doc;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public class Storage {

    public void storeUser(User user) {
        getDB().getCollection("Users").insertOne(user.toBson());
    }

    public void deleteUser(User user){
        if (user == null)
            return;
        getDB().getCollection("Users").deleteOne(getUserDocument(user.getUuid()));
    }

    public void newUserLogin(User user, String ip){
        McAuthApiApplication.getInstance().getLogger().info("New user login (" + user.toString() + ") from ip: " + ip);
        Bson filter = Filters.eq("uuid", user.getUuid());
        getDB().getCollection("Users").updateOne(filter, new Document("$set", new Document("lastLogin", System.currentTimeMillis())));
        getDB().getCollection("Users").updateOne(filter, new Document("$set", new Document("ip", ip)));

    }

    public Document getUserDocument(String uuid){
        MongoCollection<Document> collection = getDB().getCollection("Users");
        BasicDBObject query = new BasicDBObject();
        query.put("uuid", uuid);
        MongoCursor<Document> documents = collection.find(query).iterator();
        McAuthApiApplication.getInstance().getLogger().info("Querying uuid: \"" + uuid + "\"");
        if (!documents.hasNext()) {
            McAuthApiApplication.getInstance().getLogger().info("    Not  found");
            return null;
        } else {
            McAuthApiApplication.getInstance().getLogger().info("    Found");
            return documents.next();
        }
    }

    public User getUser(String uuid) {
        Document doc = getUserDocument(uuid);
        if (doc == null)
            return null;
        return User.fromBson(doc);
    }

    public void storeServer(Server server) {
        getDB().getCollection("Servers").insertOne(server.toBson());
    }

    public Document getServerDocument(String name) {
        MongoCollection<Document> collection = getDB().getCollection("Servers");
        BasicDBObject query = new BasicDBObject();
        query.put("name", name);
        MongoCursor<Document> documents = collection.find(query).iterator();
        return documents.next();
    }

    public Server getServer(String name) {
        Document doc = getServerDocument(name);
        if (doc == null)
            return null;
        return Server.fromBson(doc);
    }

    public void deleteServer(Server server){
        if (server == null)
            return;
        getDB().getCollection("Servers").deleteOne(getServerDocument(server.getName()));
    }

    private MongoDatabase getDB() {
        return McAuthApiApplication.getInstance().getMongo().getClient().getDatabase("database");
    }
}
