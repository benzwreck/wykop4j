package io.github.benzwreck.wykop4j;

import io.github.benzwreck.wykop4j.exceptions.WykopException;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.io.IOException;

class WykopHttpClient {
    private final OkHttpClient httpClient;

    WykopHttpClient(UserCredentials userCredentials, ApplicationCredentials applicationCredentials) {
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(userCredentials, applicationCredentials))
                .addInterceptor(new ApiSignInterceptor(applicationCredentials))
                .cache(null)
                .build();
    }

    public String execute(WykopRequest wykopRequest) {
        try (Response response = httpClient.newCall(WykopToOkHttpRequestMapper.map(wykopRequest)).execute()) {
            return response.body().string();
        } catch (IOException e) {
            throw new WykopException(0, e.getMessage(), e.getMessage());
        }
    }

}


