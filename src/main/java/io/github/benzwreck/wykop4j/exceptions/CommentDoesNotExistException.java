package io.github.benzwreck.wykop4j.exceptions;

/**
 * This exception is thrown when comment you are trying to fetch does not exist or was removed.
 */
public class CommentDoesNotExistException extends WykopException {
    public CommentDoesNotExistException() {
        super(81, "Comment doesn't exist or removed", "Komentarz nie istnieje");
    }
}
