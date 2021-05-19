package io.github.benzwreck.wykop4j.links;

import io.github.benzwreck.wykop4j.profiles.SimpleProfile;

import java.net.URL;

/**
 * This class represents a related link.
 */
public class RelatedLink {
    private final int id;
    private final URL url;
    private final String title;
    private final int voteCount;
    private final SimpleProfile author;

    public RelatedLink(int id, URL url, String title, int voteCount, SimpleProfile author) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.voteCount = voteCount;
        this.author = author;
    }

    /**
     * Gets related link's id.
     */
    public int id() {
        return id;
    }

    /**
     * Gets related link's url.
     */
    public URL url() {
        return url;
    }

    /**
     * Gets related link's title.
     */
    public String title() {
        return title;
    }

    /**
     * Gets related link's vote count.
     */
    public int voteCount() {
        return voteCount;
    }

    /**
     * Gets related link's author.
     */
    public SimpleProfile author() {
        return author;
    }

    @Override
    public String toString() {
        return "RelatedLink{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", voteCount=" + voteCount +
                ", author=" + author +
                '}';
    }
}
