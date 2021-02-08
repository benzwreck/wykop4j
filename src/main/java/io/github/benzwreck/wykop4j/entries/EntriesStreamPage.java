package io.github.benzwreck.wykop4j.entries;

public enum EntriesStreamPage {
    FIRST(1), SECOND(2);
    private final int number;
    EntriesStreamPage(int number){
        this.number = number;
    }
    public String value(){
        return String.valueOf(number);
    }
}
