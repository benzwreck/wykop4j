package io.github.benzwreck.wykop4j.exceptions;

public class ActionForbiddenException extends WykopException {
    public ActionForbiddenException() {
        super(552, "You do not have access", "Brak uprawnień");
    }
}
