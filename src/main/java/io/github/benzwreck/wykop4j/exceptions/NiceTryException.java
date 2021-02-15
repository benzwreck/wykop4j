package io.github.benzwreck.wykop4j.exceptions;

import io.github.benzwreck.wykop4j.WykopException;

public class NiceTryException extends WykopException {
    public NiceTryException(int errorCode, String messageEn, String messagePl) {
        super(errorCode, messageEn, messagePl);
    }
}
