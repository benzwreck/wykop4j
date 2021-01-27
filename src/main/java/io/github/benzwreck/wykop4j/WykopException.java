package io.github.benzwreck.wykop4j;

public class WykopException extends RuntimeException {

    public WykopException() {
    }

    public WykopException(String message) {
        super(message);
    }

    public WykopException(String message, Throwable cause) {
        super(message, cause);
    }

    public WykopException(Throwable cause) {
        super(cause);
    }
}
