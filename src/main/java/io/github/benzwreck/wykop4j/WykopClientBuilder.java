package io.github.benzwreck.wykop4j;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WykopClientBuilder {
    private UserCredentials userCredentials;
    private ApplicationCredentials applicationCredentials;
    private ExecutorService httpClientExecutorService;
    private ExecutorService mapperExecutorService;

    public WykopClientBuilder() {
    }

    public WykopClientBuilder userCredentials(UserCredentials userCredentials) {
        this.userCredentials = userCredentials;
        return this;
    }

    public WykopClientBuilder applicationCredentials(ApplicationCredentials applicationCredentials) {
        this.applicationCredentials = applicationCredentials;
        return this;
    }

    public WykopClientBuilder httpClientExecutorService(ExecutorService httpClientExecutorService) {
        this.httpClientExecutorService = httpClientExecutorService;
        return this;
    }

    public WykopClientBuilder mapperExecutorService(ExecutorService mapperExecutorService) {
        this.mapperExecutorService = mapperExecutorService;
        return this;
    }

    public WykopClient build() {
        if (applicationCredentials == null) {
            throw new IllegalArgumentException("Application Credentials must be provided.");
        }
        if (mapperExecutorService == null) {
            mapperExecutorService = createDefaultThreadPool("wykop-mapper");
        }
        WykopHttpClient client = new WykopHttpClient.Builder()
                .applicationCredentials(applicationCredentials)
                .userCredentials(userCredentials)
                .executorService(Objects.requireNonNullElseGet(httpClientExecutorService,
                        () -> createDefaultThreadPool("wykop-http-client")))
                .build();
        WykopObjectMapper wykopObjectMapper = new WykopObjectMapper(mapperExecutorService);
        WykopConnect wykopConnect = new WykopConnect(wykopObjectMapper, applicationCredentials);
        return new WykopClient(client, wykopObjectMapper, wykopConnect);
    }

    private ThreadPoolExecutor createDefaultThreadPool(String threadName) {
        return new ThreadPoolExecutor(0,
                Integer.MAX_VALUE,
                60,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                r -> new Thread(r, threadName));
    }
}