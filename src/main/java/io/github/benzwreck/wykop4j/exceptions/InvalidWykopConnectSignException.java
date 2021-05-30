package io.github.benzwreck.wykop4j.exceptions;

/**
 * This exception is thrown when provided wykop sign is invalid.
 */
public class InvalidWykopConnectSignException extends WykopException {
    public InvalidWykopConnectSignException() {
        super(0, "Invalid Wykop Connect sign.", "Niepoprawny podpis Wykop Connect");
    }
}
