package io.github.benzwreck.wykop4j.exceptions;

public class InvalidValueException extends WykopException {
    public InvalidValueException() {
        super(530, "Invalid value", "Niepoprawna wartość");
    }
}
