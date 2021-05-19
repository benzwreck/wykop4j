package io.github.benzwreck.wykop4j.exceptions;

/**
 * This exception is thrown when your are trying to cheat on Wykop API.
 */
public class NiceTryException extends WykopException {
    public NiceTryException() {
        super(999, "Nice try ;-)", "Nie kombinuj ;-)");
    }
}