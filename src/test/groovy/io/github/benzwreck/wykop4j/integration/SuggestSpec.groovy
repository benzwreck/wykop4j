package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import spock.lang.Specification

class SuggestSpec extends Specification {
    WykopClient wykop = IntegrationWykopClient.getInstance()

    def "should return list of tag suggestions"() {
        expect:
        !wykop.suggestTags("wykop").execute().isEmpty()
    }

    def "should return list of user suggestions"() {
        expect:
        !wykop.suggestUsers("zycie").execute().isEmpty()
    }
}
