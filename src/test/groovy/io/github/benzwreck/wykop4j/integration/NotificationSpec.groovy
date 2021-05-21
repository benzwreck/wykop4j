package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll
import spock.util.concurrent.PollingConditions

class NotificationSpec extends Specification {
    @Shared
    WykopClient wykop = IntegrationWykopClient.getInstance()

    @Unroll
    def "should not thrown an exception - that means it fetched, parsed and returned a proper object"() {
        when:
        result.execute()
        then:
        noExceptionThrown()
        where:
        result                               | _
        wykop.getDirectedNotificationCount() | _
        wykop.getDirectedNotifications()     | _
        wykop.getTagsNotifications()         | _
        wykop.getTagsNotificationCount()     | _
        wykop.getAllNotifications()          | _
        wykop.getAllNotificationCount()      | _
    }

    @Unroll
    def "should read #name notifications"() {
        given:
        PollingConditions conditions = new PollingConditions(timeout: 5, initialDelay: 1)
        when:
        before.execute()
        then:
        conditions.eventually {
            assert after.execute() == 0
        }
        where:
        before                               | after                                | name
        wykop.readAllNotifications()         | wykop.getAllNotificationCount()      | "all"
        wykop.readAllDirectedNotifications() | wykop.getDirectedNotificationCount() | "all directed"
        wykop.readAllTagsNotifications()     | wykop.getTagsNotificationCount()     | "all tags"

    }
}
