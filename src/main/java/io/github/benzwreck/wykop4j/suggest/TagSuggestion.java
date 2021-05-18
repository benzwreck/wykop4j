package io.github.benzwreck.wykop4j.suggest;

/**
 * This class contains tag suggestion.
 */
public class TagSuggestion {
    private final String tag;
    private final int followers;

    public TagSuggestion(String tag, int followers) {
        this.tag = tag;
        this.followers = followers;
    }

    /**
     * Gets the name of tag suggestion.
     */
    public String tag() {
        return tag;
    }

    /**
     * Gets the count of followers of suggested tag.
     */
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
