package io.github.benzwreck.wykop4j.exceptions;

/**
 * This exception is thrown when you do not have access to the resource.
 */
public class ActionForbiddenException extends WykopException {
    public ActionForbiddenException() {
        super(552, "You do not have access", "Brak uprawnień");
    }
}
