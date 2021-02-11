package io.github.benzwreck.wykop4j;

public class WykopException extends RuntimeException {
    protected final int code;
    protected final String messageEn;
    protected final String messagePl;

    public int code() {
        return code;
    }

    public String messageEn() {
        return messageEn;
    }

    public String messagePl() {
        return messagePl;
    }
    public WykopException(int code, String messageEn, String messagePl) {
        super(messageEn);
        this.code = code;
        this.messageEn = messageEn;
        this.messagePl = messagePl;
    }
}
