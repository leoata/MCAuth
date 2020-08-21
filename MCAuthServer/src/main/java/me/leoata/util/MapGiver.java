package me.leoata.util;

import me.leoata.listener.MapListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;

import java.awt.image.BufferedImage;

public class MapGiver implements Runnable {

    private final Player player;
    private final String secret;

    public MapGiver(Player player, String secret) {
        this.player = player;
        this.secret = secret;
    }

    @Override
    public void run() {
        MapView createdView = installRenderer(player, secret);
        //stack count 0 prevents the item from being dropped
        ItemStack mapItem = MapListener.getMap(createdView.getId());
        player.getInventory().addItem(mapItem);
        player.sendMessage(ChatColor.DARK_GREEN + "Here is your secret code. Just scan it with your phone");
        player.sendMessage(ChatColor.DARK_GREEN + "Then drop it in order to remove it");
    }

    private MapView installRenderer(Player player, String secret) {
        MapView mapView = Bukkit.createMap(player.getWorld());
        mapView.getRenderers().forEach(mapView::removeRenderer);

        mapView.addRenderer(new ImageRenderer(player, secret));
        return mapView;
    }
}
