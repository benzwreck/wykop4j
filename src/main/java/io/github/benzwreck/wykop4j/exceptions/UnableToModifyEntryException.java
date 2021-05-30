package io.github.benzwreck.wykop4j.exceptions;

/**
 * This exception is thrown when you are trying to modify a comment when somebody has already commented it.
 */
public class UnableToModifyEntryException extends WykopException {

    public UnableToModifyEntryException() {
        super(35, "You can't modify this comment or entry", "Nie możesz edytować tego komentarza");
    }
}
