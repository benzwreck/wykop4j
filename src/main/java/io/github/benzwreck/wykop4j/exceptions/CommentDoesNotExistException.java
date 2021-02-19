package io.github.benzwreck.wykop4j.exceptions;

public class CommentDoesNotExistException extends WykopException {
    public CommentDoesNotExistException() {
        super(81, "Comment doesn't exist or removed", "Komentarz nie istnieje");
    }
}
