package io.github.benzwreck.wykop4j.exceptions;

public class UnableToDeleteCommentException extends WykopException {
    public UnableToDeleteCommentException() {
        super(37, "You can't modify this comment or entry", "Nie możesz usunąć tego komentarza");
    }
}
