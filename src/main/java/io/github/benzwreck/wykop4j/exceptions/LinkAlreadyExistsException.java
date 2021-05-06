package io.github.benzwreck.wykop4j.exceptions;

public class LinkAlreadyExistsException extends WykopException {
    public LinkAlreadyExistsException() {
        super(522, "", "Znalezisko o tym adresie już istnieje");
    }
}
