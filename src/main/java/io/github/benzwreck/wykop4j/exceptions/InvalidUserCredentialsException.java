package io.github.benzwreck.wykop4j.exceptions;

public class InvalidUserCredentialsException extends WykopException {
    public InvalidUserCredentialsException() {
        super(14, "Invalid login or password", "Niepoprawny login lub hasło");
    }
}
