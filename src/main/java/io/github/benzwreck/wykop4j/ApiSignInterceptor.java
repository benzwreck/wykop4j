package io.github.benzwreck.wykop4j;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

class ApiSignInterceptor implements Interceptor {
    private final ApiSignCalculator apiSignCalculator;

    public ApiSignInterceptor(ApplicationCredentials applicationCredentials){
        this.apiSignCalculator = new ApiSignCalculator(applicationCredentials);
    }
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        String apiSign = apiSignCalculator.calculate(request);
        Request updatedRequest = request.newBuilder()
                .addHeader("apisign", apiSign)
                .build();
        return chain.proceed(updatedRequest);
    }
}
