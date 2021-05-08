package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import spock.lang.Specification

class LoginSpec extends Specification {
    WykopClient wykop = IntegrationWykopClient.getInstance()
    URL randomUrl = new URL("https://randompage.pl")

    def "should return wykop connect html page"() {
        when:
        def wykopConnectPage = wykop.getWykopConnectHtmlPage(randomUrl).execute()
        then:
        println(wykopConnectPage)
        wykopConnectPage.contains("<title>Połącz konto z aplikacją")
    }
}
