package io.github.benzwreck.wykop4j.login;

/**
 * This class contains login data provided from Wykop Connect.
 */
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

    /**
     * Gets application key.
     */
    public String appkey() {
        return appkey;
    }

    /**
     * Gets user's login.
     */
    public String login() {
        return login;
    }

    /**
     * Gets user's token.
     */
    public String token() {
        return token;
    }

    /**
     * Gets response sign.
     */
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
