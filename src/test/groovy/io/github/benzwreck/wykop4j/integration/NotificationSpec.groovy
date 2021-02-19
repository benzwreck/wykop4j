package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

class NotificationSpec extends Specification {
    WykopClient wykop = IntegrationWykopClient.getInstance()

    def "should not thrown an exception - that means it fetched, parsed and returned a proper object"() {
        when:
        wykop.directNotifications().execute()
        wykop.directNotificationCount().execute()
        wykop.tagsNotifications().execute()
        wykop.tagsNotificationCount().execute()
        wykop.allNotifications().execute()
        wykop.allNotificationCount().execute()
        then:
        noExceptionThrown()
    }

    def "should read all notifications"() {
        PollingConditions conditions = new PollingConditions(timeout: 5, initialDelay: 1)
        when:
        wykop.readAllNotifications().execute()
        then:
        conditions.eventually {
            assert wykop.allNotificationCount().execute() == 0
        }
    }
}
