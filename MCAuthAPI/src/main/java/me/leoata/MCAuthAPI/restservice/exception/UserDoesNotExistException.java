package me.leoata.MCAuthAPI.restservice.exception;

import me.leoata.MCAuthAPI.restservice.registration.exception.RegistrationFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.NOT_FOUND, reason="User does not exist.")
public class UserDoesNotExistException extends MCAuthException {
}
