package io.github.benzwreck.wykop4j;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

class WykopHttpClient {
    private final OkHttpClient httpClient;

    WykopHttpClient(UserCredentials userCredentials, ApplicationCredentials applicationCredentials) {
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(userCredentials, applicationCredentials))
                .build();
    }
    WykopHttpClient(OkHttpClient httpClient){
        this.httpClient = httpClient;
    }
    public String execute(WykopRequest wykopRequest) {
        Request request = wykopRequest.toOkHttpRequest();
        try (Response response = httpClient.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            throw new WykopException(e);
        }
    }

}


