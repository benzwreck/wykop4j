package io.github.benzwreck.wykop4j.exceptions;

public class UserCannotObserveThemselfException extends WykopException {
    public UserCannotObserveThemselfException() {
        super(33, "User can't observe themself", "Nie możesz obserwować lub zablokować siebie");
    }
}
