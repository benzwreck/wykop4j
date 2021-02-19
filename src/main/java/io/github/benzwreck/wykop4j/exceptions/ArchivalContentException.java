package io.github.benzwreck.wykop4j.exceptions;

public class ArchivalContentException extends WykopException {
    public ArchivalContentException() {
        super(24, "Archival content", "Przeglądasz archiwalną treść");
    }
}
