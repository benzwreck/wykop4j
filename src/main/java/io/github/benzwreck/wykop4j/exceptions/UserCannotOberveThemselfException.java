package io.github.benzwreck.wykop4j.exceptions;

public class UserCannotOberveThemselfException extends WykopException {
    public UserCannotOberveThemselfException() {
        super(33, "User can't observe themself", "Nie możesz obserwować lub zablokować siebie");
    }
}
