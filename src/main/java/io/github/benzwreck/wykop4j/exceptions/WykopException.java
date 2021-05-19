package io.github.benzwreck.wykop4j.exceptions;

/**
 * This exception is base Wykop exception.
 */
public class WykopException extends RuntimeException {
    protected final int code;
    protected final String messageEn;
    protected final String messagePl;

    public WykopException(int code, String messageEn, String messagePl) {
        super("code: " + code + "\nmessage en: " + messageEn + "\nmessage pl: " + messagePl);
        this.code = code;
        this.messageEn = messageEn;
        this.messagePl = messagePl;
    }

    /**
     * Gets exception's code.
     */
    public int code() {
        return code;
    }

    /**
     * Gets exception's message in English.
     */
    public String messageEn() {
        return messageEn;
    }

    /**
     * Gets exception's message in Polish.
     */
    public String messagePl() {
        return messagePl;
    }
}
