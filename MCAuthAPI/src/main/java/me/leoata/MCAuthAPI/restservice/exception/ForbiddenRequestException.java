package me.leoata.MCAuthAPI.restservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.FORBIDDEN, reason="Forbidden request.")
public class ForbiddenRequestException extends MCAuthException {
}
