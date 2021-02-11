package io.github.benzwreck.wykop4j.exceptions;

import io.github.benzwreck.wykop4j.WykopException;

public class ArchivalContentException extends WykopException {
    public ArchivalContentException(int code, String messageEn, String messagePl) {
        super(code, messageEn, messagePl);
    }
}
