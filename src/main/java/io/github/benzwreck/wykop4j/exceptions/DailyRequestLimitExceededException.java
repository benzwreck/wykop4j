package io.github.benzwreck.wykop4j.exceptions;

public class DailyRequestLimitExceededException extends WykopException {
    public DailyRequestLimitExceededException() {
        super(5, "Daily requests limit exceeded", "Limit zapytań został wykorzystany");
    }
}
