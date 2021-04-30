package io.github.benzwreck.wykop4j.links;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class LinkDraft {
    private final String key;
    private final String sourceUrl;
    private final String title;
    private final String description;
    private final String tags;
    private final List<Link> duplicates;

    public LinkDraft(String key, String sourceUrl, String title, String description, String tags, List<Link> duplicates) {
        this.key = key;
        this.sourceUrl = sourceUrl;
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.duplicates = duplicates;
    }

    public String key() {
        return key;
    }

    public String sourceUrl() {
        return sourceUrl;
    }

    public List<Link> duplicates() {
        return Collections.unmodifiableList(duplicates);
    }

    public Optional<String> title() {
        return Optional.of(title);
    }

    public Optional<String> description() {
        return Optional.of(description);
    }

    public Optional<String> tags() {
        return Optional.of(tags);
    }

    @Override
    public String toString() {
        return "LinkDraft{" +
                "key='" + key + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tags='" + tags + '\'' +
                ", duplicates=" + duplicates +
                '}';
    }
}
