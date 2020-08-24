package me.leoata.util;

import lombok.Getter;

public class User {

    @Getter private String uuid;
    private String ip;
    private long lastLogin;

    public User(String uuid, String ip, long lastLogin){
        this.uuid = uuid;
        this.ip = ip;
        this.lastLogin = lastLogin;
    }

    public long getLastLogin() {
        return lastLogin;
    }

    public String getIp() {
        return ip;
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid='" + uuid + '\'' +
                ", ip='" + ip + '\'' +
                ", lastLogin=" + lastLogin +
                '}';
    }
}
