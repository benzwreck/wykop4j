package io.github.benzwreck.wykop4j.exceptions;

import io.github.benzwreck.wykop4j.WykopException;

public class ActionForbiddenException extends WykopException {
    public ActionForbiddenException(int code, String messageEn, String messagePl) {
        super(code, messageEn, messagePl);
    }
}