package me.leoata.MCAuthAPI.restservice.auth.exception;

import me.leoata.MCAuthAPI.restservice.exception.MCAuthException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.FORBIDDEN, reason="Authentication failed.")
public class AuthenticationFailureException extends MCAuthException { }
