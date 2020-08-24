package me.leoata.listener;

import com.mysql.jdbc.TimeUtil;
import me.leoata.MCAuthPlugin;
import me.leoata.impl.APIClient;
import me.leoata.util.BukkitUtils;
import me.leoata.util.JavaUtil;
import me.leoata.util.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ProtectionListener implements Listener {

    private List<UUID> frozen = new ArrayList<>();
    private static final String AUTH_REQUIRED_MESSAGE = ChatColor.RED + "You need to first authenticate with the MCAuth app.";
    private final HashMap<UUID, Long> messageCooldown = new HashMap<>();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (handleFreeze(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (handleFreeze(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockZ() == event.getTo().getBlockZ())
            return;
        if (handleFreeze(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (frozen.contains(event.getPlayer().getUniqueId()) && !event.getMessage().startsWith("/mcauth")) {
            event.setCancelled(true);
            if (System.currentTimeMillis() - messageCooldown.getOrDefault(event.getPlayer().getUniqueId(), 0L) > TimeUnit.HOURS.toMillis(24))
                return;
            event.getPlayer().sendMessage(AUTH_REQUIRED_MESSAGE);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (needsAuthentication(player)) {
            Bukkit.getScheduler().runTaskAsynchronously(MCAuthPlugin.getInstance(), () -> {
                User user = APIClient.getUser(player.getUniqueId().toString());
                long millisSinceLastLogin = (System.currentTimeMillis() - (user == null ? 0 : user.getLastLogin())) -
                        // Account for a timezone difference
                        TimeUnit.MILLISECONDS.toHours(19);
                String playerIP = event.getPlayer().getAddress().getAddress().getHostAddress();
                if (user == null
                        || !playerIP.equals(user.getIp())
                        || millisSinceLastLogin > TimeUnit.SECONDS.toMillis(30)) {
                    freeze(player);
                } else {
                    BukkitUtils.staffBroadcast(player.getName() + " has successfully authenticated with MCAuth.");
                }
            });
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        frozen.remove(event.getPlayer().getUniqueId());
    }

    public boolean needsAuthentication(Player player) {
        //Generate a random string to test if the player didn't just deny the permission mcauth.require to themselves.
        return player.hasPermission("mcauth.require") || player.hasPermission(JavaUtil.randomString(8) + '.' + JavaUtil.randomString(8)) || player.isOp();
    }

    public void freeze(Player player) {
        player.sendMessage(AUTH_REQUIRED_MESSAGE);
        frozen.add(player.getUniqueId());
    }

    public boolean handleFreeze(Player player){
        if (frozen.contains(player.getUniqueId())) {
            if (System.currentTimeMillis() - messageCooldown.getOrDefault(player.getUniqueId(), 0L) > TimeUnit.SECONDS.toMillis(3))
                return true;
            player.sendMessage(AUTH_REQUIRED_MESSAGE);
            return true;
        }
        return false;
    }
}
