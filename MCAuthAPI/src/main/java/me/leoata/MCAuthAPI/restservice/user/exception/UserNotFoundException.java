package me.leoata.MCAuthAPI.restservice.user.exception;

import me.leoata.MCAuthAPI.restservice.exception.MCAuthException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND, reason="User not found.")
public class UserNotFoundException extends MCAuthException { }
