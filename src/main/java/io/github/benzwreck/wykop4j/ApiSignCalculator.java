package io.github.benzwreck.wykop4j;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.security.MessageDigest;

class ApiSignCalculator {
    private final ApplicationCredentials applicationCredentials;

    public ApiSignCalculator(ApplicationCredentials applicationCredentials) {
        this.applicationCredentials = applicationCredentials;
    }

    public String calculate(Request request) {
        String secret = applicationCredentials.secret();
        String url = request.url().toString();
        StringBuilder post = new StringBuilder();
        RequestBody body = request.body();
        if (body instanceof MultipartBody) {
            MultipartBody multipartBody = (MultipartBody) body;
            for (int i = 0; i < multipartBody.parts().size(); i++) {
                RequestBody part = multipartBody.part(i).body();
                if(part instanceof FormBody){
                    FormBody formBody = (FormBody) part;
                    for (int j = 0; j < formBody.size()-1; j++) {
                        post.append(formBody.encodedValue(j)).append(",");
                    }
                    post.append(formBody.encodedValue(formBody.size() - 1));
                    break;
                }
            }
        }
        String data = secret + url + post.toString();
        return md5(data);
    }

    private String md5(String data) {
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(data.getBytes());
            byte[] digest = messageDigest.digest();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
