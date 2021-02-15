package io.github.benzwreck.wykop4j.exceptions;

public class NiceTryException extends WykopException {
    public NiceTryException(int errorCode, String messageEn, String messagePl) {
        super(errorCode, messageEn, messagePl);
    }
}
