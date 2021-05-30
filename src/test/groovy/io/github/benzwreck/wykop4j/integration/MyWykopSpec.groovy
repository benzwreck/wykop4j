package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import io.github.benzwreck.wykop4j.profiles.ActionType
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class MyWykopSpec extends Specification {
    @Shared
    WykopClient wykop = IntegrationWykopClient.getInstance()

    @Unroll
    def "should retrieve mywykop index data"() {
        when:
        action.execute()
        then:
        noExceptionThrown()
        where:
        action                                           | _
        wykop.getMyWykopIndexActions()                   | _
        wykop.getMyWykopIndexActions(ActionType.ENTRIES) | _
        wykop.getMyWykopIndexActions(ActionType.LINKS)   | _
        wykop.getMyWykopTagsActions()                    | _
        wykop.getMyWykopTagsActions(ActionType.ENTRIES)  | _
        wykop.getMyWykopTagsActions(ActionType.LINKS)    | _
        wykop.getMyWykopUsersActions()                   | _
        wykop.getMyWykopUsersActions(ActionType.ENTRIES) | _
        wykop.getMyWykopUsersActions(ActionType.LINKS)   | _
        wykop.getMyWykopEntries()                        | _
        wykop.getMyWykopLinks()                          | _
    }
}
