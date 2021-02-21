package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationData
import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.WykopClient
import spock.lang.Specification

class ConversationSpec extends Specification {
    WykopClient wykop = IntegrationWykopClient.getInstance()
    private String nonexistentLogin = UUID.randomUUID().toString()

    def "should not thrown an exception"() {
        when:
        wykop.conversationsList().execute()
        wykop.conversation(IntegrationData.conversationReceiverLogin).execute()
        then:
        noExceptionThrown()
    }

    def "should return empty list"() {
        when:
        def execute = wykop.conversation(nonexistentLogin).execute()
        then:
        execute.isEmpty()
    }
}
