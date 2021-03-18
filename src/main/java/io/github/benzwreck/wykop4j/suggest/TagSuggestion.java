package io.github.benzwreck.wykop4j.suggest;

public class TagSuggestion {
    private final String tag;
    private final int followers;

    public TagSuggestion(String tag, int followers) {
        this.tag = tag;
        this.followers = followers;
    }

    public String tag() {
        return tag;
    }

    public int followers() {
        return followers;
    }

    @Override
    public String toString() {
        return "TagSuggestion{" +
                "tag='" + tag + '\'' +
                ", followers=" + followers +
                '}';
    }
}
