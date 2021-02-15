package io.github.benzwreck.wykop4j.exceptions;

public class UnableToDeleteCommentException extends WykopException {
    public UnableToDeleteCommentException(int errorCode, String messageEn, String messagePl) {
        super(errorCode, messageEn, messagePl);
    }
}
