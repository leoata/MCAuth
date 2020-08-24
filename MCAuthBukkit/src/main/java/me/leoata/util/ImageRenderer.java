package me.leoata.util;

import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import lombok.Getter;
import lombok.SneakyThrows;
import me.leoata.command.RegisterUserCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

public class ImageRenderer extends MapRenderer {

    private final UUID player;
    private String secret;

    public ImageRenderer(Player player, String secret) {
        super(true);

        this.secret = secret;
        this.player = player.getUniqueId();
    }

    @Override
    public void render(MapView map, MapCanvas canvas, Player player) {
        //the image is just for the player who requested a new key
        if (secret != null && player.getUniqueId().equals(this.player)) {
            try {
                canvas.drawImage(0, 0, MatrixToImageWriter.toBufferedImage(TwoFAUtil.createQRCode(
                        TwoFAUtil.getGoogleAuthenticatorBarCode(secret, player.getUniqueId().toString()), 128, 128)));
            } catch (WriterException e) {
                e.printStackTrace();
            }
            //release resources in order to prevent memory leaks
            secret = null;
        }
    }
}
