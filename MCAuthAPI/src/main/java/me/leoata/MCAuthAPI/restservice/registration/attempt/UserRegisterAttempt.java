package me.leoata.MCAuthAPI.restservice.registration.attempt;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class UserRegisterAttempt {
    //Server information
    @Getter private String key;
    @Getter private String name;

    @Getter private String uuid;
    @Getter private String ip;
}
