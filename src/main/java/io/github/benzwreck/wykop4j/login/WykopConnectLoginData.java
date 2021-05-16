package io.github.benzwreck.wykop4j.login;

public class WykopConnectLoginData {
    private final String appkey;
    private final String login;
    private final String token;
    private final String sign;

    public WykopConnectLoginData(String appkey, String login, String token, String sign) {
        this.appkey = appkey;
        this.login = login;
        this.token = token;
        this.sign = sign;
    }

    public String appkey() {
        return appkey;
    }

    public String login() {
        return login;
    }

    public String token() {
        return token;
    }

    public String sign() {
        return sign;
    }

    @Override
    public String toString() {
        return "WykopConnectLoginData{" +
                "appkey='" + appkey + '\'' +
                ", login='" + login + '\'' +
                ", token='" + token + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
