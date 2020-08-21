package me.leoata.MCAuthAPI;

import lombok.Getter;
import me.leoata.MCAuthAPI.storage.Storage;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;

public class Authenticator {
    @Getter private Storage storage;
    public Authenticator(){
        this.storage = new Storage();
    }
    public String getUUID(String username){
        String url = "https://api.minetools.eu/uuid/"+username;
        try {
            @SuppressWarnings("deprecation")
            String UUIDJson = IOUtils.toString(new URL(url));
            if(UUIDJson.isEmpty()) return null;
            JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(UUIDJson);
            return UUIDObject.get("id").toString().replace("-", "");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
    public String getUsername(String uuid){
        String url = "https://api.minetools.eu/uuid/"+uuid;
        try {
            @SuppressWarnings("deprecation")
            String UUIDJson = IOUtils.toString(new URL(url));
            if(UUIDJson.isEmpty()) return null;
            JSONObject UUIDObject = (JSONObject) JSONValue.parseWithException(UUIDJson);
            return UUIDObject.get("name").toString();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

}
