package io.github.benzwreck.wykop4j.exceptions;

public class UnableToModifyEntryException extends WykopException {

    public UnableToModifyEntryException(int code, String messageEn, String messagePl) {
        super(code, messageEn, messagePl);
    }
}
