package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import spock.lang.Specification

class LinkSpec extends Specification {
    WykopClient wykop = IntegrationWykopClient.getInstance()

    def "should return promoted links"() {
        when:
        def links = wykop.promotedLinks().execute()
        then:
        links.stream().allMatch(link -> link.status() == "promoted")
    }
}
