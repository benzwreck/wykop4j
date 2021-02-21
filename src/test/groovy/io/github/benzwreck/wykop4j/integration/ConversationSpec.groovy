package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import spock.lang.Specification

class ConversationSpec extends Specification{
    WykopClient wykop = IntegrationWykopClient.getInstance()

    def "should return list of conversations"(){
        when:
        wykop.conversationsList().execute()
        then:
        noExceptionThrown()
    }
}
