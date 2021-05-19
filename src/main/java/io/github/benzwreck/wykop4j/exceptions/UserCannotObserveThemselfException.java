package io.github.benzwreck.wykop4j.exceptions;

/**
 * This exception is thrown when you are trying to observe yourself.
 */
public class UserCannotObserveThemselfException extends WykopException {
    public UserCannotObserveThemselfException() {
        super(33, "User can't observe themself", "Nie możesz obserwować lub zablokować siebie");
    }
}
