package me.leoata.MCAuthAPI.restservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.BAD_REQUEST, reason="Malformed request.")
public class MalformedAttemptException extends MCAuthException{
}
