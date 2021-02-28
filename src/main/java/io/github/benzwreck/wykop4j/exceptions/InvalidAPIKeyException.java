package io.github.benzwreck.wykop4j.exceptions;

public class InvalidAPIKeyException extends WykopException {
    public InvalidAPIKeyException() {
        super(1, "Invalid API key", "Niepoprawny klucz API");
    }
}
