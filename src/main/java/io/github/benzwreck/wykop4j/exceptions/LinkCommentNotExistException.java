package io.github.benzwreck.wykop4j.exceptions;

public class LinkCommentNotExistException extends WykopException {
    public LinkCommentNotExistException() {
        super(515, "You connot reply on deleted objects", "Nie ma takiego komentarza");
    }
}
