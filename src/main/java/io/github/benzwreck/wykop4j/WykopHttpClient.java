package io.github.benzwreck.wykop4j;

import io.github.benzwreck.wykop4j.exceptions.WykopException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

class WykopHttpClient {
    private final OkHttpClient httpClient;

    private WykopHttpClient(OkHttpClient okHttpClient) {
        this.httpClient = okHttpClient;
    }

    public String execute(WykopRequest wykopRequest) {
        try (Response response = httpClient.newCall(WykopToOkHttpRequestMapper.map(wykopRequest)).execute()) {
            return response.body().string();
        } catch (IOException e) {
            throw new WykopException(0, e.getMessage(), e.getMessage());
        }
    }

    public CompletableFuture<String> executeAsync(WykopRequest wykopRequest) {
        CompletableFuture<String> future = new CompletableFuture<>();
        try {
            httpClient.newCall(WykopToOkHttpRequestMapper.map(wykopRequest)).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    future.completeExceptionally(e);
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    future.complete(response.body().string());
                }
            });
            return future;
        } catch (IOException e) {
            throw new WykopException(0, e.getMessage(), e.getMessage());
        }
    }

    public static final class Builder {
        private UserCredentials userCredentials;
        private ApplicationCredentials applicationCredentials;
        private OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        public Builder() {
        }

        public Builder userCredentials(UserCredentials userCredentials) {
            this.userCredentials = userCredentials;
            return this;
        }

        public Builder applicationCredentials(ApplicationCredentials applicationCredentials) {
            this.applicationCredentials = applicationCredentials;
            return this;
        }

        public Builder executorService(ExecutorService executorService) {
            this.okHttpClientBuilder.dispatcher(new Dispatcher(executorService));
            return this;
        }

        public WykopHttpClient build() {
            OkHttpClient okHttpClient = okHttpClientBuilder
                    .addInterceptor(new AuthInterceptor(userCredentials, applicationCredentials))
                    .addInterceptor(new ApiSignInterceptor(applicationCredentials))
                    .cache(null)
                    .build();
            return new WykopHttpClient(okHttpClient);
        }
    }
}


