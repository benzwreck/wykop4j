package io.github.benzwreck.wykop4j;

import io.github.benzwreck.wykop4j.exceptions.WykopException;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class AuthInterceptor implements Interceptor {
    private final UserCredentials userCredentials;
    private final ApplicationCredentials applicationCredentials;

    private String userKey;

    AuthInterceptor(UserCredentials userCredentials, ApplicationCredentials applicationCredentials) {
        this.userCredentials = userCredentials;
        this.applicationCredentials = applicationCredentials;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request mainRequest = chain.request();
        StringBuilder updatedStringBuilder = new StringBuilder(mainRequest.url().toString())
                .append("appkey/").append(applicationCredentials.appKey());
        if (userCredentials.isEmpty()) {
            Request request = mainRequest.newBuilder()
                    .url(updatedStringBuilder.toString())
                    .build();
            return chain.proceed(request);
        }
        if (userKey == null) {
            userKey = provideUserKey(chain);
        }
        updatedStringBuilder.append("/userkey/").append(userKey).append("/");
        Request request = mainRequest.newBuilder()
                .url(updatedStringBuilder.toString())
                .build();
        Response proceed = chain.proceed(request);
        String body = peekBody(proceed.body());
        if (isUserKeyNotUpToDate(body)) {
            userKey = provideUserKey(chain);
            Request updatedRequest = updateRequest(request);
            return chain.proceed(updatedRequest);
        }
        return proceed;
    }

    private String provideUserKey(Chain chain) throws IOException {
        Request authRequest = prepareAuthRequest();
        Response authResponse = chain.proceed(authRequest);
        String authBody = authResponse.body().string();
        return extractUserKey(authBody);
    }

    private String peekBody(ResponseBody body) throws IOException {
        /*
        Can not use body.string() to get the String value of response.
        Response once consumed (body.string()) cannot be consumed twice.
         */
        BufferedSource source = body.source();
        source.request(Long.MAX_VALUE);
        return source.getBuffer().snapshot().utf8();
    }

    private Request prepareAuthRequest() {
        FormBody authFormBody = prepareAuthBody();
        String url = "https://a2.wykop.pl/Login/Index/appkey/" + applicationCredentials.appKey();
        return new Request.Builder()
                .url(url)
                .post(authFormBody)
                .build();
    }

    private FormBody prepareAuthBody() {
        return new FormBody.Builder()
                .add("accountkey", userCredentials.accountKey())
                .build();
    }

    private boolean isUserKeyNotUpToDate(String proceed) {
        return proceed.contains("\"data\":null,\"error\":{\"code\":11");
    }

    private Request updateRequest(Request mainRequest) {
        String mainRequestUrl = mainRequest.url().toString();
        String updatedUrl = updateUrl(mainRequestUrl);
        return mainRequest.newBuilder()
                .url(updatedUrl)
                .build();
    }

    private String updateUrl(String mainRequestUrl) {
        return mainRequestUrl.replaceFirst("/userkey/(.*)/", "/userkey/" + userKey + "/");
    }

    private String extractUserKey(String authResponseString) {
        Pattern pattern = Pattern.compile("\"userkey\":\"(.*)\"");
        Matcher matcher = pattern.matcher(authResponseString);
        if (matcher.find()) {
            return matcher.group(1);
        } else
            throw new WykopException(0, "Could not retrieve userkey\n" + authResponseString, "Nie można pobrać klucza użytkownika.");
    }
}
