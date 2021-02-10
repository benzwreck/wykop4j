package io.github.benzwreck.wykop4j;

import com.fasterxml.jackson.core.type.TypeReference;
import io.github.benzwreck.wykop4j.entries.Entry;
import io.github.benzwreck.wykop4j.entries.Page;

import java.util.List;
import java.util.Optional;

public class WykopClient {
    private final static String WYKOP_URL = "https://a2.wykop.pl";
    private final WykopHttpClient client;
    private final WykopObjectMapper wykopObjectMapper;

    WykopClient(WykopHttpClient wykopHttpClient, WykopObjectMapper wykopObjectMapper) {
        this.client = wykopHttpClient;
        this.wykopObjectMapper = wykopObjectMapper;
    }

    /**
     * @return First page of the latest Microblog's Entries.
     */
    public Chain<List<Entry>> entriesStream() {
        return entriesStream(Page.of(1));
    }

    /**
     * @param page available pages for Entries' Stream. Has to be 1 or 2.
     * @return Given page of the latest Microblog's Entries.
     * @throws IllegalArgumentException if page is different than 1 or 2.
     */
    public Chain<List<Entry>> entriesStream(Page page) {
        if (page.value() < 0 || page.value() > 2)
            throw new IllegalArgumentException("Page" + page + "is forbidden. Only page 1 and page 2 are possible to fetch.");
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/Stream/page/int/")
                .namedParam("page", String.valueOf(page.value()))
                .build(), new TypeReference<List<Entry>>() {
        });
    }

    /**
     * @param entryId id from where we start counting except entryId
     * @return Microblog's Entries where first {@link Entry} id equals first one after entryId.
     */
    public Chain<List<Entry>> entriesStream(int entryId) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/Stream/firstid/int/")
                .namedParam("firstid", String.valueOf(entryId))
                .build(), new TypeReference<List<Entry>>() {
        });
    }

    /**
     * @param id id of the {@link Entry} you are looking for.
     * @return possible {@link Entry}
     */
    public Chain<Optional<Entry>> entry(int id) {
        return new Chain<>(new WykopRequest.Builder()
                .url(WYKOP_URL + "/Entries/Entry/entry/")
                .apiParam("entry", String.valueOf(id))
                .build(), new TypeReference<Optional<Entry>>() {
        });
    }

    public static final class Builder {
        private UserCredentials userCredentials;
        private ApplicationCredentials applicationCredentials;

        public Builder() {
        }

        public Builder withUserCredentials(UserCredentials userCredentials) {
            this.userCredentials = userCredentials;
            return this;
        }

        public Builder withApplicationCredentials(ApplicationCredentials applicationCredentials) {
            this.applicationCredentials = applicationCredentials;
            return this;
        }

        public WykopClient build() {
            WykopHttpClient client = new WykopHttpClient(userCredentials, applicationCredentials);
            WykopObjectMapper wykopObjectMapper = new WykopObjectMapper();
            return new WykopClient(client, wykopObjectMapper);
        }
    }

    public class Chain<T> {
        private final WykopRequest wykopRequest;
        private Class<T> clazz;
        private TypeReference<T> typeReference;

        Chain(WykopRequest wykopRequest, Class<T> clazz) {
            this.wykopRequest = wykopRequest;
            this.clazz = clazz;
        }

        Chain(WykopRequest wykopRequest, TypeReference<T> typeReference) {
            this.wykopRequest = wykopRequest;
            this.typeReference = typeReference;
        }

        public T execute() {
            String response = WykopClient.this.client.execute(wykopRequest);
            if (clazz != null) {
                return WykopClient.this.wykopObjectMapper.map(response, clazz);
            } else
                return WykopClient.this.wykopObjectMapper.map(response, typeReference);

        }

    }

}
