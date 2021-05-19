package io.github.benzwreck.wykop4j.exceptions;

/**
 * This exception is thrown when user you are looking for does not exist or has deleted the account.
 */
public class UserNotFoundException extends WykopException {
    public UserNotFoundException() {
        super(13, "User not found, User doesn't exist", "Brak użytkownika");
    }
}
