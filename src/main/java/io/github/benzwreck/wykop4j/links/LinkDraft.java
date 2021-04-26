package io.github.benzwreck.wykop4j.links;

import java.util.Collections;
import java.util.List;

public class LinkDraft {
    private final String key;
    private final String sourceUrl;
    private final List<Link> duplicates;

    public LinkDraft(String key, String sourceUrl, List<Link> duplicates) {
        this.key = key;
        this.sourceUrl = sourceUrl;
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

    @Override
    public String toString() {
        return "LinkDraft{" +
                "key='" + key + '\'' +
                ", sourceUrl='" + sourceUrl + '\'' +
                ", duplicates=" + duplicates +
                '}';
    }
}
