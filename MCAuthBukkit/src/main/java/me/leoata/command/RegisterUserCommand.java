package me.leoata.command;

import me.leoata.MCAuthPlugin;
import me.leoata.brevis.command.Command;
import me.leoata.brevis.command.Param;
import me.leoata.impl.APIClient;
import me.leoata.util.ImageRenderer;
import me.leoata.util.JavaUtil;
import me.leoata.util.MapGiver;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RegisterUserCommand {

    private static List<UUID> registering = new ArrayList<>();

    @Command(names = {"mcauth register"}, permission = "op", async = true)
    public static void registerUser(Player sender, @Param(name = "user") Player player) {
        String uuid = player.getUniqueId().toString();
        String ip = player.getAddress().getAddress().getHostAddress();
        if (APIClient.getUser(uuid) != null) {
            sender.sendMessage(ChatColor.RED + "User is already registered or is currently registering");
            return;
        }
        String response = APIClient.registerUser(uuid, ip);
        if (response == null || JavaUtil.isInteger(response)) {
            if (response != null && response.equals("403"))
                sender.sendMessage(ChatColor.RED + "You need to set your private key and server name in your config.yml");
            else
                sender.sendMessage(ChatColor.RED + "There was an error processing your request.");
            return;
        }
        String secret;
        try {
            secret = getSecretFromResponse(response);
        } catch (ParseException e) {
            sender.sendMessage(ChatColor.RED + "There was an error processing your request.");
            return;
        }
        sender.sendMessage(ChatColor.GREEN + "Successfully registered the user " + player.getName() +
                ". They must complete the registration by opening Google Authenticator and scanning the barcode on the map.");
        if (sender.getUniqueId() != player.getUniqueId())
            player.sendMessage("You were successfully registered! Complete the registration by opening Google Authenticator and scanning the barcode on the map.");
        Bukkit.getScheduler().runTask(MCAuthPlugin.getInstance(), new MapGiver(player, secret));
    }

    private static String getSecretFromResponse(String response) throws ParseException {
        JSONObject jsonObj = (JSONObject) JSONValue.parseWithException(response);
        return (String) jsonObj.get("twoFaSecret");
    }

    public static List<UUID> getRegistering() {
        return registering;
    }
}
