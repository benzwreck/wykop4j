package io.github.benzwreck.wykop4j.exceptions;

public class UserBlockedByAnotherUserException extends WykopException {
    public UserBlockedByAnotherUserException() {
        super(102, "Użytkownik nie chce odbierać wiadomości prywatnych od Ciebie", "User does not want to receive messages from you.");
    }
}
