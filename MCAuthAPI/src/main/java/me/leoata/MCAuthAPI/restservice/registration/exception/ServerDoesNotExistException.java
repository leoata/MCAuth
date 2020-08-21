package me.leoata.MCAuthAPI.restservice.registration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.BAD_REQUEST, reason="Server does not exist.")
public class ServerDoesNotExistException extends RegistrationFailureException{
}
