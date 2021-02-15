package io.github.benzwreck.wykop4j.exceptions;

public class ActionForbiddenException extends WykopException {
    public ActionForbiddenException(int code, String messageEn, String messagePl) {
        super(code, messageEn, messagePl);
    }
}
