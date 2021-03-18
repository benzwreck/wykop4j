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
        action                                        | _
        wykop.myWykopIndexActions()                   | _
        wykop.myWykopIndexActions(ActionType.ENTRIES) | _
        wykop.myWykopIndexActions(ActionType.LINKS)   | _
        wykop.myWykopTagsActions()                    | _
        wykop.myWykopTagsActions(ActionType.ENTRIES)  | _
        wykop.myWykopTagsActions(ActionType.LINKS)    | _
        wykop.myWykopUsersActions()                   | _
        wykop.myWykopUsersActions(ActionType.ENTRIES) | _
        wykop.myWykopUsersActions(ActionType.LINKS)   | _
        wykop.myWykopEntries()                        | _
    }

}
