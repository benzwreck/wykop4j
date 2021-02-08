package io.github.benzwreck.wykop4j;

public class ApplicationCredentials {
    private final String appKey;
    private final String secret;

    public ApplicationCredentials(String appKey, String secret) {
        this.appKey = appKey;
        this.secret = secret;
    }

    public String appKey() {
        return appKey;
    }

    public String secret() {
        return secret;
    }
}