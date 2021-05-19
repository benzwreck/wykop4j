package io.github.benzwreck.wykop4j.exceptions;

/**
 * This exception is thrown when you try to edit comment with answers.
 */
public class CannotEditCommentsWithAnswerException extends WykopException {
    public CannotEditCommentsWithAnswerException() {
        super(529, "You cannot edit comments with answers", "Nie można edytować komentarza na który ktoś odpowiedział");
    }
}
