package io.github.benzwreck.wykop4j.exceptions;

public class UserNotFoundException extends WykopException {
    public UserNotFoundException() {
        super(13, "User not found, User doesn't exist", "Brak użytkownika");
    }
}
