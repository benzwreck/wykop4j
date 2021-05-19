package io.github.benzwreck.wykop4j.exceptions;

/**
 * This exception is thrown when provided credentials are invalid.
 */
public class InvalidUserCredentialsException extends WykopException {
    public InvalidUserCredentialsException() {
        super(14, "Invalid login or password", "Niepoprawny login lub hasło");
    }
}
