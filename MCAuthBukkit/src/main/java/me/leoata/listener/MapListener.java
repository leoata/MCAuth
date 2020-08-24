package me.leoata.listener;

import lombok.Getter;
import me.leoata.command.RegisterUserCommand;
import me.leoata.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class MapListener implements Listener {

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (RegisterUserCommand.getRegistering().contains(event.getPlayer().getUniqueId()) &&
                event.getPlayer().getItemInHand() != null &&
                event.getPlayer().getItemInHand().isSimilar(getMap((short) -1))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        for (ItemStack is : player.getInventory().getContents()) {
            if (is != null && is.isSimilar(getMap((short) -1))) {
                RegisterUserCommand.getRegistering().remove(event.getPlayer().getUniqueId());
                player.getInventory().remove(is);
            }
        }
    }

    public static ItemStack getMap(short id){
        return new ItemBuilder(Material.MAP).displayName("&c2FA Registration Map").data(id).build();
    }
}
