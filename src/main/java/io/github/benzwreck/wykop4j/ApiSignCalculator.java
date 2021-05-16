package io.github.benzwreck.wykop4j;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.BufferedSink;
import okio.Okio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.StringJoiner;

class ApiSignCalculator {
    private final ApplicationCredentials applicationCredentials;

    public ApiSignCalculator(ApplicationCredentials applicationCredentials) {
        this.applicationCredentials = applicationCredentials;
    }

    public String calculate(Request request) throws IOException {
        String secret = applicationCredentials.secret();
        String url = request.url().toString();
        if (secret.isEmpty()) return MD5Encoder.encode(url);
        StringJoiner post = new StringJoiner(",");
        RequestBody body = request.body();
        if (body instanceof FormBody) {
            FormBody formBody = (FormBody) body;
            for (int j = 0; j < formBody.size(); j++) {
                post.add(formBody.value(j));
            }
        } else if (body instanceof MultipartBody) {
            MultipartBody multipartBody = (MultipartBody) body;
            for (int i = 0; i < multipartBody.parts().size(); i++) {
                RequestBody part = multipartBody.part(i).body();
                if (part.contentType() == null) {
                    BufferedSink bufferedSink = Okio.buffer(Okio.sink(new ByteArrayOutputStream()));
                    part.writeTo(bufferedSink);
                    String buffer = bufferedSink.getBuffer().readUtf8();
                    post.add(buffer);
                }
            }
        }
        String data = secret + url + post;
        return MD5Encoder.encode(data);
    }
}
