package io.github.benzwreck.wykop4j.exceptions;

/**
 * This exception is thrown when you are trying to login in with invalid API key.
 */
public class InvalidAPIKeyException extends WykopException {
    public InvalidAPIKeyException() {
        super(1, "Invalid API key", "Niepoprawny klucz API");
    }
}
