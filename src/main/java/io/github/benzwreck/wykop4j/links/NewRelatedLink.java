package io.github.benzwreck.wykop4j.links;

import java.net.URL;

public class NewRelatedLink {
    private final String title;
    private final URL url;
    private final boolean isAdultOnly;

    private NewRelatedLink(String title, URL url, boolean isAdultOnly) {
        this.title = title;
        this.url = url;
        this.isAdultOnly = isAdultOnly;
    }

    public String title() {
        return title;
    }

    public URL url() {
        return url;
    }

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
                throw new IllegalStateException("Title and url must be provided.");
            return new NewRelatedLink(title, url, isAdultOnly);
        }
    }
}
