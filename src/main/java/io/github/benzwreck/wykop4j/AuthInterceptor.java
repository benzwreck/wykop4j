package io.github.benzwreck.wykop4j;

import io.github.benzwreck.wykop4j.exceptions.WykopException;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSource;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class AuthInterceptor implements Interceptor {
    private final UserCredentials userCredentials;
    private final ApplicationCredentials applicationCredentials;

    private String userKey;

    public AuthInterceptor(UserCredentials userCredentials, ApplicationCredentials applicationCredentials) {
        this.userCredentials = userCredentials;
        this.applicationCredentials = applicationCredentials;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request mainRequest = chain.request();
        String mainRequestUrl = mainRequest.url().toString();
        Request.Builder authRequestBuilder = chain.request()
                .newBuilder()
                .url(mainRequestUrl + "appkey/" + applicationCredentials.appKey() + "/userkey/" + userKey + "/");
        Request request = authRequestBuilder.build();
        Response proceed = chain.proceed(request);
        /*
        Can not use proceed.body.string() to get the String value of response.
        Response once consumed (proceed.body.string()) cannot be consumed twice.
         */
        // -----------------------
        /*
        dodaj appkey do url
        jesli usercredentials != empty to
            jesli userkey != null to
                dodaj userkey do url
                    jesli odpowiedz jest bledem to
                        odswiez userkey, zamien i ponownie wykonaj żądanie i je zwróć
            jesli userkey == null
                pobierz userkey, ustaw i ponownie wykonaj żądanie i je zwróć
        jesli usercredentials == empty to
            wykonaj żądanie i je zwróć -> przemauje na obiekt albo na autorization exception
         */
        BufferedSource source = proceed.body().source();
        source.request(Long.MAX_VALUE);
        String body = source.getBuffer().snapshot().utf8();
        // -----------------------

        if (isUserKeyNotUpToDate(body)) {
            Request authRequest = prepareAuthRequest();
            Response authResponse = chain.proceed(authRequest);
            String authBody = authResponse.body().string();
            userKey = extractUserKey(authBody);
            Request updatedRequest = updateRequest(mainRequest);
            return chain.proceed(updatedRequest);
        }
        return proceed;
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
                .add("login", userCredentials.login())
                .build();
    }

    private boolean isUserKeyNotUpToDate(String proceed) {
        return proceed.contains("\"data\":null,\"error\":");
    }

    private Request updateRequest(Request mainRequest) {
        String mainRequestUrl = mainRequest.url().toString();
        String updatedUrl = updateUrl(mainRequestUrl);
        return mainRequest.newBuilder()
                .url(updatedUrl)
                .build();
    }

    private String updateUrl(String mainRequestUrl) {
        if (mainRequestUrl.contains("userkey")) {
            return mainRequestUrl.replaceFirst("userkey/(.*)", "userkey/" + userKey + "/");
        }
        return mainRequestUrl + "appkey/" + applicationCredentials.appKey() + "/userkey/" + userKey + "/";
    }

    private String extractUserKey(String authResponseString) {
        Pattern pattern = Pattern.compile("\"userkey\":\"(.*)\"");
        Matcher matcher = pattern.matcher(authResponseString);
        if (matcher.find()) {
            return matcher.group(1);
        } else throw new WykopException(0, "Could not extract userkey.", "Nie można pobrać klucza użytkownika.");
    }
}
