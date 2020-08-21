package me.leoata.MCAuthAPI.restservice.registration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.BAD_REQUEST, reason="Server already exists.")
public class ServerAlreadyExistsException extends RegistrationFailureException {
}
