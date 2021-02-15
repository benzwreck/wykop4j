package io.github.benzwreck.wykop4j.exceptions;

public class LimitExceededException extends WykopException {
    public LimitExceededException(int code, String messageEn, String messagePl) {
        super(code, messageEn, messagePl);
    }
}
