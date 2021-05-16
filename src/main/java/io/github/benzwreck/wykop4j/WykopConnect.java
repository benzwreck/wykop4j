package io.github.benzwreck.wykop4j;

import io.github.benzwreck.wykop4j.exceptions.InvalidWykopConnectSignException;
import io.github.benzwreck.wykop4j.login.WykopConnectLoginData;

import java.net.URL;
import java.util.Base64;

class WykopConnect {
    private final WykopObjectMapper wykopObjectMapper;
    private final ApplicationCredentials applicationCredentials;

    WykopConnect(WykopObjectMapper wykopObjectMapper, ApplicationCredentials applicationCredentials) {
        this.wykopObjectMapper = wykopObjectMapper;
        this.applicationCredentials = applicationCredentials;
    }

    String getSecureCode(URL redirectURL) {
        return MD5Encoder.encode(applicationCredentials.secret() + redirectURL.toString());
    }

    String encodeURL(URL redirectURL) {
        return Base64.getEncoder().encodeToString(redirectURL.toString().getBytes());
    }

    WykopConnectLoginData parseResponse(URL response) {
        String connectData = response.toString().replaceFirst("(.*)connectData=", "");
        byte[] decodedResponse = Base64.getDecoder().decode(connectData);
        String loginData = new String(decodedResponse);
        WykopConnectLoginData wykopConnectLoginData = wykopObjectMapper.map(loginData, WykopConnectLoginData.class);
        if (!wykopConnectLoginData.sign().equals(MD5Encoder.encode(applicationCredentials.secret() + wykopConnectLoginData.appkey() + wykopConnectLoginData.login() + wykopConnectLoginData.token()))) {
            throw new InvalidWykopConnectSignException();
        }
        return wykopConnectLoginData;
    }
}
