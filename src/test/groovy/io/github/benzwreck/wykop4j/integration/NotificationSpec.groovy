package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import spock.lang.Specification

class NotificationSpec extends Specification {
    WykopClient wykop = IntegrationWykopClient.getInstance()

    def "should not thrown an exception - that means it fetched, parsed and returned a proper object"() {
        when:
        wykop.directNotifications().execute()
        wykop.notificationsCount().execute()
        wykop.tagsNotifications().execute()
        then:
        noExceptionThrown()
    }
}
