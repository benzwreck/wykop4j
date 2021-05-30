package io.github.benzwreck.wykop4j.exceptions;

/**
 * This exception is thrown when you exceeded your request limit. You have to wait a few minutes.<br>
 * How many? God, I wish I knew.
 */
public class LimitExceededException extends WykopException {
    public LimitExceededException() {
        super(506, "Limit exceeded", "Przekroczony limit, zaczekaj kilka minut");
    }
}
