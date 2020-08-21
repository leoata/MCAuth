package me.leoata.MCAuthAPI.restservice.registration.exception;

import me.leoata.MCAuthAPI.restservice.exception.MCAuthException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.BAD_REQUEST, reason="Registration failed.")
public class RegistrationFailureException extends MCAuthException { }
