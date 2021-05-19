package io.github.benzwreck.wykop4j.exceptions;

/**
 * This exception is thrown when you do not have permission to do such operation.
 */
public class AuthorizationException extends WykopException {
    public AuthorizationException() {
        super(7, "This application does not have permission to do this", "Ta aplikacja nie ma uprawnień do wykonania tej operacji");
    }
}
