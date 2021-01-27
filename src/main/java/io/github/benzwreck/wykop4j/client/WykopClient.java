package io.github.benzwreck.wykop4j.client;

public class WykopClient {
    private final static String WYKOP_URL = "https://a2.wykop.pl";
    private final WykopHttpClient client;

    private WykopClient(UserCredentials userCredentials, ApplicationCredentials applicationCredentials){
        client = new WykopHttpClient(userCredentials, applicationCredentials);
    }


    public String allHotEntries(int duration) {
        WykopRequest allHotEntriesRequest = new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/Hot/period/int/")
//                .namedParam("page", "1")
                .namedParam("period", String.valueOf(duration))
                .build();
        return client.execute(allHotEntriesRequest);
    }


    public Chain getAllEntries(int duration) {
        return new Chain(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/Hot/period/int/")
                .namedParam("period", String.valueOf(duration))
                .build());
    }

    public class Chain {
        private final WykopRequest wykopRequest;

        Chain(WykopRequest wykopRequest) {
            this.wykopRequest = wykopRequest;
        }

        public String execute() {
            return WykopClient.this.client.execute(wykopRequest);
        }

    }
    public static final class Builder{
        private UserCredentials userCredentials;
        private ApplicationCredentials applicationCredentials;
        private Builder(){
        }
        public Builder withUserCredentials(UserCredentials userCredentials){
            this.userCredentials = userCredentials;
            return this;
        }
        public Builder withApplicationCredentials(ApplicationCredentials applicationCredentials){
            this.applicationCredentials = applicationCredentials;
            return this;
        }
        public WykopClient build(){
            return new WykopClient(userCredentials, applicationCredentials);
        }
    }
}
