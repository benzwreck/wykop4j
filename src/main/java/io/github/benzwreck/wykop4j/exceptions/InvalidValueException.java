package io.github.benzwreck.wykop4j.exceptions;

/**
 * This exception is thrown when some mandatory request's fields are missing.
 */
public class InvalidValueException extends WykopException {
    public InvalidValueException() {
        super(530, "Invalid value", "Niepoprawna wartość");
    }
}
