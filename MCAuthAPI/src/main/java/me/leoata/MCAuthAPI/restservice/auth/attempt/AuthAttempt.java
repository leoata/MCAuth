package me.leoata.MCAuthAPI.restservice.auth.attempt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class AuthAttempt {

    @Getter private String auth;
    @Getter private String ip;
    @Getter private String uuid;


}
