package io.github.benzwreck.wykop4j;

public class Page {
    private final int number;

    private Page(int number) {
        this.number = number;
    }

    public static Page of(int number) {
        return new Page(number);
    }

    public int value() {
        return number;
    }
}
