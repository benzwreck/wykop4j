package io.github.benzwreck.wykop4j.exceptions;

/**
 * This exception is thrown when you trying to reply on deleted link's comment.
 */
public class LinkCommentNotExistException extends WykopException {
    public LinkCommentNotExistException() {
        super(515, "You cannot reply on deleted objects", "Nie ma takiego komentarza");
    }
}
