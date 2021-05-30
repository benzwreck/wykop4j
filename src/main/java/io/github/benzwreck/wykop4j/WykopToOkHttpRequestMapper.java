package io.github.benzwreck.wykop4j;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

class WykopToOkHttpRequestMapper {
    static Request map(WykopRequest wykopRequest) throws IOException {
        {
            Request.Builder requestBuilder = new Request.Builder();
            String tempUrl = wykopRequest.url();
            // api params:
            for (Map.Entry<String, String> entry : wykopRequest.apiParams().entrySet()) {
                tempUrl = tempUrl.replace(entry.getKey(), entry.getValue());
            }
            // named params:
            for (Map.Entry<String, String> entry : wykopRequest.namedParams().entrySet()) {
                tempUrl = tempUrl.replaceFirst(entry.getKey() + "/(\\w*)/", entry.getKey() + "/" + entry.getValue() + "/");
            }
            if (wykopRequest.clearOutput()) tempUrl += "output/clear/";
            if (wykopRequest.fullData()) tempUrl += "data/full/";
            requestBuilder.url(tempUrl);

            if (wykopRequest.postParams().isEmpty() && wykopRequest.file() == null) {
                return requestBuilder.build();
            }
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            // post params:
            for (Map.Entry<String, String> entry : wykopRequest.postParams().entrySet()) {
                builder.addFormDataPart(entry.getKey(), entry.getValue());
            }
            if (wykopRequest.file() != null) {
                String contentType = Files.probeContentType(wykopRequest.file().toPath());
                builder
                        .addFormDataPart("embed", wykopRequest.shownFileName(),
                                RequestBody.create(wykopRequest.file(), MediaType.parse(contentType)));
            }

            return requestBuilder
                    .post(builder.build())
                    .build();
        }
    }
}
