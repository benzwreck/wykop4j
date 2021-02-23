package io.github.benzwreck.wykop4j.exceptions;

public class LimitExceededException extends WykopException {
    public LimitExceededException() {
        super(506, "Limit exceeded", "Przekroczony limit, zaczekaj kilka minut");
    }
}
