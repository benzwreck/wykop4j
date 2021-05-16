package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.ApplicationCredentials
import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import io.github.benzwreck.wykop4j.exceptions.InvalidWykopConnectSignException
import spock.lang.Specification

import java.security.MessageDigest

class LoginSpec extends Specification {
    WykopClient wykop = IntegrationWykopClient.getInstance()
    ApplicationCredentials applicationCredentials = IntegrationWykopClient.applicationCredentials
    String wykopConnectHtmlResponse = "<title>Połącz konto z aplikacją"

    def "should return wykop connect html page with redirect url"() {
        given:
        URL randomUrl = new URL("https://randompage.com")
        when:
        def wykopConnectPage = wykop.getWykopConnectHtmlPage(randomUrl).execute()
        then:
        wykopConnectPage.contains(wykopConnectHtmlResponse)
    }

    def "should return wykop connect html page without redirect url"() {
        when:
        def wykopConnectPage = wykop.getWykopConnectHtmlPage().execute()
        then:
        wykopConnectPage.contains(wykopConnectHtmlResponse)
    }

    def "should return proper login data"() {
        given:
        String validLogin = "KochamWykopAPI"
        String validToken = "ZLepszymNigdyNiePracowalem"
        String validSign = md5(applicationCredentials.secret() + applicationCredentials.appKey() + validLogin + validToken)
        URL validResponse = prepareTestUrl(applicationCredentials, validLogin, validToken, validSign)
        when:
        def loginData = wykop.parseWykopConnectLoginResponse(validResponse)
        then:
        with loginData, {
            appkey() == applicationCredentials.appKey()
            login() == validLogin
            token() == validToken
            sign() == validSign
        }
    }

    def "should throw an exception"() {
        given:
        String validLogin = "KochamWykopAPI"
        String validToken = "ZLepszymNigdyNiePracowalem"
        String invalidSign = "blablabla"
        URL invalidResponse = prepareTestUrl(applicationCredentials, validLogin, validToken, invalidSign)
        when:
        wykop.parseWykopConnectLoginResponse(invalidResponse)
        then:
        thrown InvalidWykopConnectSignException
    }

    private static String md5(String data) {
        StringBuilder sb = new StringBuilder()
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5")
            messageDigest.update(data.getBytes())
            byte[] digest = messageDigest.digest()
            for (byte b : digest) {
                sb.append(String.format("%02x", b))
            }
        } catch (Exception e) {
            e.printStackTrace()
        }
        return sb.toString()
    }

    private static URL prepareTestUrl(ApplicationCredentials applicationCredentials, String login, String token, String sign) {
        String randomUrl = "https://randompage.com/dsdsadsa-4444-3333-b222-11111?connectData="
        String connectData = Base64.getEncoder()
                .encodeToString("""{"appkey":"${applicationCredentials.appKey()}","login":"$login","token":"$token","sign":"$sign"}""".getBytes())
        URL validResponse = new URL(randomUrl + connectData)
        validResponse
    }

}