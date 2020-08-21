package me.leoata.MCAuthAPI.types;

import lombok.Getter;
import lombok.Setter;

public class RegisteredUser {
    @Getter private String uuid;
    @Getter private String ip;
    @Getter private long lastLogin;
    @Getter@Setter private String twoFaSecret;

    public RegisteredUser(User user){
        this.uuid = user.getUuid();
        this.ip = user.getIp();
        this.lastLogin = user.getLastLogin();
        this.twoFaSecret = user.getTwoFaSecret();
    }
}
