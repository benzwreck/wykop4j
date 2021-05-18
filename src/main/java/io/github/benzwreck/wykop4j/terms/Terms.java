package io.github.benzwreck.wykop4j.terms;

/**
 * This class contains Wykop Terms.
 */
public class Terms {
    private final String html;
    private final String text;

    public Terms(String html, String text) {
        this.html = html;
        this.text = text;
    }

    /**
     * Gets the html version of Wykop Terms with html tags.
     */
    public String html() {
        return html;
    }

    /**
     * Gets plain text version of Wykop Terms.
     */
    public String text() {
        return text;
    }

    @Override
    public String toString() {
        return "Terms{" +
                "html='" + html + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
