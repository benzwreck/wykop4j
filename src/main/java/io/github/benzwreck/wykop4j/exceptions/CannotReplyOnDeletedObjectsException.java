package io.github.benzwreck.wykop4j.exceptions;

public class CannotReplyOnDeletedObjectsException extends WykopException {
    public CannotReplyOnDeletedObjectsException() {
        super(517, "You connot reply on deleted objects", "Nie można usunąć komentarza na który ktoś odpowiedział");
    }
}
