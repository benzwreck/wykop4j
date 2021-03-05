package io.github.benzwreck.wykop4j.profiles;

import io.github.benzwreck.wykop4j.entries.Entry;
import io.github.benzwreck.wykop4j.links.Link;

import java.util.List;

public class Actions {
    private final List<Entry> entries;
    private final List<Link> links;

    public Actions(List<Entry> entries, List<Link> links) {
        this.entries = entries;
        this.links = links;
    }

    public List<Entry> entries() {
        return entries;
    }

    public List<Link> links() {
        return links;
    }

    @Override
    public String toString() {
        return "Actions{" +
                "entries=" + entries +
                ", links=" + links +
                '}';
    }
}
