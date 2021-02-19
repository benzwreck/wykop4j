package io.github.benzwreck.wykop4j.exceptions;

public class UnableToModifyEntryException extends WykopException {

    public UnableToModifyEntryException() {
        super(35, "You can't modify this comment or entry", "Nie możesz edytować tego komentarza");
    }
}
