package io.github.benzwreck.wykop4j.links;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * This class represents link's draft used to create a new link.
 */
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

    /**
     * Gets link draft's key.
     */
    public String key() {
        return key;
    }

    /**
     * Gets link draft's source url.
     */
    public String sourceUrl() {
        return sourceUrl;
    }

    /**
     * Gets link draft's possible duplicates.
     */
    public List<Link> duplicates() {
        return Collections.unmodifiableList(duplicates);
    }

    /**
     * Gets possible link draft's title.
     */
    public Optional<String> title() {
        return Optional.of(title);
    }

    /**
     * Gets possible link draft's description.
     */
    public Optional<String> description() {
        return Optional.of(description);
    }

    /**
     * Gets possible link draft's tags.
     */
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
