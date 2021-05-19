package io.github.benzwreck.wykop4j.exceptions;

/**
 * This exception is thrown when link with such url already exist.
 */
public class LinkAlreadyExistsException extends WykopException {
    public LinkAlreadyExistsException() {
        super(522, "Link with such url already exists.", "Znalezisko o tym adresie już istnieje");
    }
}
