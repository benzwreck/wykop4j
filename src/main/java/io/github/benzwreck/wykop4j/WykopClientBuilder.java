package io.github.benzwreck.wykop4j;

public class WykopClientBuilder {
    private UserCredentials userCredentials;
    private ApplicationCredentials applicationCredentials;

    public WykopClientBuilder() {
    }

    public WykopClientBuilder userCredentials(UserCredentials userCredentials) {
        this.userCredentials = userCredentials;
        return null;
    }

    public WykopClientBuilder applicationCredentials(ApplicationCredentials applicationCredentials) {
        this.applicationCredentials = applicationCredentials;
        return null;
    }

    public WykopClient build() {
        if (applicationCredentials == null) {
            throw new IllegalArgumentException("Application Credentials must be provided.");
        }
        WykopHttpClient.Builder client = new WykopHttpClient.Builder()
                .applicationCredentials(applicationCredentials)
                .userCredentials(userCredentials);
        WykopObjectMapper wykopObjectMapper = new WykopObjectMapper();
        WykopConnect wykopConnect = new WykopConnect(wykopObjectMapper, applicationCredentials);
        return new WykopClient(client.build(), wykopObjectMapper, wykopConnect);
    }
}