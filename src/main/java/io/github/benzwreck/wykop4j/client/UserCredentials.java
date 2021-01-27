package io.github.benzwreck.wykop4j.client;

public class UserCredentials {
    private final String login;
    private final String password;

    UserCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String login() {
        return login;
    }
    public String password(){
        return password;
    }
}