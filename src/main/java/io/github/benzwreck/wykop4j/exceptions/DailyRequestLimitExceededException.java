package io.github.benzwreck.wykop4j.exceptions;

/**
 * This exception is thrown when you exceeded your daily request limit.
 */
public class DailyRequestLimitExceededException extends WykopException {
    public DailyRequestLimitExceededException() {
        super(5, "Daily requests limit exceeded", "Limit zapytań został wykorzystany");
    }
}
