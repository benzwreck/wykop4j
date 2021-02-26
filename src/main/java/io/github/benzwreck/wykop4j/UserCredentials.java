package io.github.benzwreck.wykop4j;

public class UserCredentials {
    private final String login;
    private final String accountKey;

    public UserCredentials(String login, String accountKey) {
        this.login = login;
        this.accountKey = accountKey;
    }

    public String login() {
        return login;
    }
    public String accountKey(){
        return accountKey;
    }
}