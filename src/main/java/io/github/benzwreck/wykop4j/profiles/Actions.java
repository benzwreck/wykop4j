package io.github.benzwreck.wykop4j.profiles;

import io.github.benzwreck.wykop4j.entries.Entry;
import io.github.benzwreck.wykop4j.links.Link;

import java.util.Collections;
import java.util.List;

/**
 * This class contains user's actions - both entries and links.
 */
public class Actions {
    private final List<Entry> entries;
    private final List<Link> links;

    public Actions(List<Entry> entries, List<Link> links) {
        this.entries = entries;
        this.links = links;
    }

    /**
     * Gets entries added by user.
     */
    public List<Entry> entries() {
        return Collections.unmodifiableList(entries);
    }

    /**
     * Gets links added by user.
     */
    public List<Link> links() {
        return Collections.unmodifiableList(links);
    }

    @Override
    public String toString() {
        return "Actions{" +
                "entries=" + entries +
                ", links=" + links +
                '}';
    }
}
