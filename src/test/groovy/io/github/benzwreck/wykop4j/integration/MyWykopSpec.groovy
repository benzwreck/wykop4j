package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import io.github.benzwreck.wykop4j.profiles.ActionType
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class MyWykopSpec extends Specification {
    @Shared WykopClient wykop = IntegrationWykopClient.getInstance()

    @Unroll
    def "should retrieve mywykop index data"() {
        when:
        action.execute()
        then:
        noExceptionThrown()
        where:
        action                                 | _
        wykop.myWykopIndex()                   | _
        wykop.myWykopIndex(ActionType.ENTRIES) | _
        wykop.myWykopIndex(ActionType.LINKS)   | _
    }
}
