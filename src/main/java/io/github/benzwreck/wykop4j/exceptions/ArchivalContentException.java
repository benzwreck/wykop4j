package io.github.benzwreck.wykop4j.exceptions;

/**
 * This exception is thrown when the content your are trying to get is archival.
 */
public class ArchivalContentException extends WykopException {
    public ArchivalContentException() {
        super(24, "Archival content", "Przeglądasz archiwalną treść");
    }
}
