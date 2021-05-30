package io.github.benzwreck.wykop4j.exceptions;

/**
 * This exception is thrown when you are trying to delete a comment when somebody has already commented it.
 */
public class UnableToDeleteCommentException extends WykopException {
    public UnableToDeleteCommentException() {
        super(37, "You can't modify this comment or entry", "Nie możesz usunąć tego komentarza");
    }
}
