package io.github.benzwreck.wykop4j.exceptions;

public class NiceTryException extends WykopException {
    public NiceTryException() {
        super(999, "Nice try ;-)", "Nie kombinuj ;-)");
    }
}