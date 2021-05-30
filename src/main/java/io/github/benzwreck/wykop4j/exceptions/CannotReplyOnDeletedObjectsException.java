package io.github.benzwreck.wykop4j.exceptions;

/**
 * This exception is thrown when you try to reply on deleted object.
 */
public class CannotReplyOnDeletedObjectsException extends WykopException {
    public CannotReplyOnDeletedObjectsException() {
        super(517, "You cannot reply on deleted objects", "Nie można usunąć komentarza na który ktoś odpowiedział");
    }
}
