package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import spock.lang.Specification

class TermsSpec extends Specification {
    WykopClient wykop = IntegrationWykopClient.getInstance()

    def "should return non-null and non-empty response"() {
        expect:
        with wykop.getTerms().execute(), {
            html() != null
            !html().isBlank()
            text() != null
            !text().isBlank()
        }
    }

    def "should confirm terms"() {
        expect:
        wykop.confirmTerms().execute() == true
    }
}