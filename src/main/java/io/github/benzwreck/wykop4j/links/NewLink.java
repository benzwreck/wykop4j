package io.github.benzwreck.wykop4j.links;

import java.net.URL;
import java.util.Optional;

/**
 * This class contains a recipe for a new link.<br>
 * All fields except photoKey must be given to create {@link NewLink}.
 * To create a new class use {@link NewLink.Builder}.
 */
public class NewLink {
    private final String key;
    private final String title;
    private final String description;
    private final String tags;
    private final String photoKey;
    private final URL url;
    private final boolean isAdult;

    private NewLink(String key, String title, String description, String tags, String photoKey, URL url, boolean isAdult) {
        this.key = key;
        this.title = title;
        this.description = description;
        this.tags = tags;
        this.photoKey = photoKey;
        this.url = url;
        this.isAdult = isAdult;
    }

    /**
     * Gets new link's key.
     */
    public String key() {
        return key;
    }

    /**
     * Gets new link's title.
     */
    public String title() {
        return title;
    }

    /**
     * Gets new link's description.
     */
    public String description() {
        return description;
    }

    /**
     * Gets new link's tags.
     */
    public String tags() {
        return tags;
    }

    /**
     * Gets possible new link's photo key.
     */
    public Optional<String> photoKey() {
        return Optional.of(photoKey);
    }

    /**
     * Gets new link's url.
     */
    public URL url() {
        return url;
    }

    /**
     * Returns if new link content is adult only.
     */
    public boolean isAdult() {
        return isAdult;
    }

    public static final class Builder {
        private String key;
        private String title;
        private String description;
        private String tags;
        private String photoKey;
        private URL url;
        private boolean isAdult = false;

        public Builder() {
        }

        public Builder(NewLink newLink) {
            this.key = newLink.key;
            this.title = newLink.title;
            this.description = newLink.description;
            this.tags = newLink.tags;
            this.photoKey = newLink.photoKey;
            this.url = newLink.url;
            this.isAdult = newLink.isAdult;
        }

        public Builder withKey(String key) {
            this.key = key;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withTags(String tags) {
            this.tags = tags;
            return this;
        }

        public Builder withPhotoKey(String photoKey) {
            this.photoKey = photoKey;
            return this;
        }

        public Builder withUrl(URL url) {
            this.url = url;
            return this;
        }

        public Builder adultOnly() {
            this.isAdult = true;
            return this;
        }

        public NewLink build() {
            if (key == null || title == null || description == null || tags == null || url == null)
                throw new IllegalArgumentException("Fields: key, title, description, tags and url must be provided.");
            return new NewLink(key, title, description, tags, photoKey, url, isAdult);
        }
    }
}
