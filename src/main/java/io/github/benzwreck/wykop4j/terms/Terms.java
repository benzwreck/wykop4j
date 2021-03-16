package io.github.benzwreck.wykop4j.terms;

public class Terms {
    private final String html;
    private final String text;

    public Terms(String html, String text) {
        this.html = html;
        this.text = text;
    }

    public String html() {
        return html;
    }

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
