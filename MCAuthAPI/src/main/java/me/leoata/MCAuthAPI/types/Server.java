package me.leoata.MCAuthAPI.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.leoata.MCAuthAPI.restservice.registration.attempt.ServerRegisterAttempt;
import org.bson.Document;

@AllArgsConstructor
public class Server {
    @Getter private String name;
    @Getter private String key;

    public Server(ServerRegisterAttempt attempt){
        this.name = attempt.getName();
        this.key = attempt.getKey();
    }

    public static Server fromBson(Document document) {
        return new Server(document.getString("name"), document.getString("key"));
    }

    public Document toBson() {
        Document document = new Document();
        document.put("name", this.name);
        document.put("key", this.key);
        return document;
    }

    @Override
    public String toString() {
        return "Server{" +
                "name='" + name + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
