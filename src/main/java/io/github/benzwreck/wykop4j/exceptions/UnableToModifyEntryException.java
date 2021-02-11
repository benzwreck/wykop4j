package io.github.benzwreck.wykop4j.exceptions;

import io.github.benzwreck.wykop4j.WykopException;

public class UnableToModifyEntryException extends WykopException {
    private final int code;
    private final String messageEn;
    private final String messagePl;

    public UnableToModifyEntryException(int code, String messageEn, String messagePl) {
        super(messageEn);
        this.code = code;
        this.messageEn = messageEn;
        this.messagePl = messagePl;
    }

    public int code() {
        return code;
    }

    public String messageEn() {
        return messageEn;
    }

    public String messagePl() {
        return messagePl;
    }
}
