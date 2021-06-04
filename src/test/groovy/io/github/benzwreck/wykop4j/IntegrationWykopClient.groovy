package io.github.benzwreck.wykop4j

class IntegrationWykopClient {
    private static WykopClient wykop
    private static String secondAccountLogin
    private static String appkey
    private static String secret

    static String appkey() {
        if (appkey == null) {
            Properties properties = credentialsProperties()
            appkey = properties.getProperty("appkey")
        }
        appkey
    }

    static String secret() {
        if (secret == null) {
            Properties properties = credentialsProperties()
            secret = properties.getProperty("secret")
        }
        secret
    }

    static String secondAccountLogin() {
        if (secondAccountLogin == null) {
            Properties properties = credentialsProperties()
            secondAccountLogin = properties.getProperty("secondLogin")
        }
        secondAccountLogin
    }

    static WykopClient getInstance() {
        if (wykop == null) {
            Properties properties = credentialsProperties()
            String login = properties.getProperty("login")
            String accountkey = properties.getProperty("accountkey")
            String appkey = properties.getProperty("appkey")
            String secret = properties.getProperty("secret") != null ? properties.getProperty("secret") : ""
            UserCredentials userCredentials = new UserCredentials(login, accountkey)
            ApplicationCredentials applicationCredentials = new ApplicationCredentials(appkey, secret)
            wykop = new WykopClientBuilder()
                    .userCredentials(userCredentials)
                    .applicationCredentials(applicationCredentials)
                    .build()
        }
        wykop
    }

    private static Properties credentialsProperties() {
        String file = new File("src/test/resources").getAbsolutePath()
        FileInputStream propertiesStream = new FileInputStream(file + "/credentials.properties")
        Properties properties = new Properties()
        properties.load(propertiesStream)
        properties
    }
}
