package io.github.benzwreck.wykop4j.exceptions;

/**
 * This exception is thrown when you are trying to send a pm to the user that blocked you.
 */
public class UserBlockedByAnotherUserException extends WykopException {
    public UserBlockedByAnotherUserException() {
        super(102, "Użytkownik nie chce odbierać wiadomości prywatnych od Ciebie", "User does not want to receive messages from you.");
    }
}
