package me.leoata.impl;

import me.leoata.MCAuthPlugin;
import me.leoata.util.HTTPUtil;
import me.leoata.util.JavaUtil;
import me.leoata.util.User;
import net.minecraft.server.v1_7_R4.HttpUtilities;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class APIClient {

    public static String registerUser(String uuid, String ip) {
        YamlConfiguration config = MCAuthPlugin.getInstance().getConfig();

        return HTTPUtil.httpPost("register_user", "{\n" +
                "    \"name\": \"" + config.getString("server.name") + "\",\n" +
                "    \"key\": \"" + config.getString("server.secretKey") + "\",\n" +
                "    \"uuid\": \"" + uuid.replace("-", "") + "\",\n" +
                "    \"ip\": \"" + ip + "\"\n" +
                "}");
    }

    public static User getUser(String uuid) {
        YamlConfiguration config = MCAuthPlugin.getInstance().getConfig();
        String s = HTTPUtil.httpGet("user/" + uuid.replace("-", ""), "{\n" +
                "    \"name\": \"" + config.getString("server.name") + "\",\n" +
                "    \"key\": \"" + config.getString("server.secretKey") + "\"\n" +
                "}");
        if (s == null || JavaUtil.isInteger(s)) {
            return null;
        }
        JSONObject response = null;
        try {
            response = (JSONObject) JSONValue.parseWithException(s);
            return new User(uuid.replace("-", ""), (String) response.get("ip"), (Long) response.get("lastLogin"));
        } catch (Exception e) {
            Bukkit.getLogger().info("Could not find user " + uuid);
            e.printStackTrace();
            return null;
        }
    }
}
