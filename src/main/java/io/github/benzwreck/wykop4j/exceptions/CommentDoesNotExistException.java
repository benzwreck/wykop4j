package io.github.benzwreck.wykop4j.exceptions;

public class CommentDoesNotExistException extends WykopException {
    public CommentDoesNotExistException(int errorCode, String messageEn, String messagePl) {
        super(errorCode, messageEn, messagePl);
    }
}
