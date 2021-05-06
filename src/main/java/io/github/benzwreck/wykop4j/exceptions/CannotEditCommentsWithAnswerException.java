package io.github.benzwreck.wykop4j.exceptions;

public class CannotEditCommentsWithAnswerException extends WykopException {
    public CannotEditCommentsWithAnswerException() {
        super(529, "You connot edit comments with answers", "Nie można edytować komentarza na który ktoś odpowiedział");
    }
}
