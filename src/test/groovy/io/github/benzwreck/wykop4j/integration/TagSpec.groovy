package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import spock.lang.Specification

class TagSpec extends Specification {
    WykopClient wykop = IntegrationWykopClient.getInstance()

    def "should return non-empty actions"() {
        when:
        def actions = wykop.tagActions("wykopapi").execute()
        then:
        !actions.entries().isEmpty() || !actions.links().isEmpty()
    }

    def "should return an empty actions"(){
        when:
        def actions = wykop.tagActions("ponsaddnasdnsaodnasodnodnaonandoanoan").execute()
        then:
        actions.entries().isEmpty() && actions.links().isEmpty()
    }
}
