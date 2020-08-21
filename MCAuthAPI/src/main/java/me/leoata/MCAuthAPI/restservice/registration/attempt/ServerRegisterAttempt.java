package me.leoata.MCAuthAPI.restservice.registration.attempt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ServerRegisterAttempt {
    @Getter private String name;
    @Getter private String key;

    @Getter private String masterKey;
}
