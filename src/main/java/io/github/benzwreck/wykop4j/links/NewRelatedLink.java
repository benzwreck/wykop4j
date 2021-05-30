package io.github.benzwreck.wykop4j.links;

import java.net.URL;

/**
 * This class contains a recipe for a new related link.<br>
 * All fields except photoKey must be given to create {@link NewRelatedLink}.
 * To create a new class use {@link NewRelatedLink.Builder}.
 */
public class NewRelatedLink {
    private final String title;
    private final URL url;
    private final boolean isAdultOnly;

    private NewRelatedLink(String title, URL url, boolean isAdultOnly) {
        this.title = title;
        this.url = url;
        this.isAdultOnly = isAdultOnly;
    }

    /**
     * Gets new related link's title.
     */
    public String title() {
        return title;
    }

    /**
     * Gets new related link's url.
     */
    public URL url() {
        return url;
    }

    /**
     * Returns if new related link's content is adult only.
     */
    public boolean isAdultOnly() {
        return isAdultOnly;
    }

    @Override
    public String toString() {
        return "NewRelatedLink{" +
                "title='" + title + '\'' +
                ", url=" + url +
                ", isAdultOnly=" + isAdultOnly +
                '}';
    }

    public static final class Builder {
        private String title;
        private URL url;
        private boolean isAdultOnly = false;

        public Builder() {
        }

        public Builder(NewRelatedLink newRelatedLink) {
            this.title = newRelatedLink.title;
            this.url = newRelatedLink.url;
            this.isAdultOnly = newRelatedLink.isAdultOnly;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withUrl(URL url) {
            this.url = url;
            return this;
        }

        public Builder adultOnly() {
            this.isAdultOnly = true;
            return this;
        }

        public NewRelatedLink build() {
            if (title == null || url == null)
                throw new IllegalArgumentException("Title and url must be provided.");
            return new NewRelatedLink(title, url, isAdultOnly);
        }
    }
}
