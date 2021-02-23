package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationData
import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.TwoAccounts
import io.github.benzwreck.wykop4j.WykopClient
import io.github.benzwreck.wykop4j.conversations.NewMessage
import io.github.benzwreck.wykop4j.exceptions.UserNotFoundException
import spock.lang.Specification

class ConversationSpec extends Specification {
    WykopClient wykop = IntegrationWykopClient.getInstance()
    private String nonexistentLogin = UUID.randomUUID().toString()

    def "should not thrown an exception"() {
        when:
        wykop.conversationsList().execute()
        wykop.conversation(IntegrationData.secondAccountLogin).execute()
        then:
        noExceptionThrown()
    }

    def "should return empty list"() {
        when:
        def execute = wykop.conversation(nonexistentLogin).execute()
        then:
        execute.isEmpty()
    }

    @TwoAccounts
    def "should send message with body and url media"() {
        def newMessage = new NewMessage.Builder()
                .withBody("tresc")
                .withMedia("https://www.wykop.pl/cdn/c3201142/comment_1613001626Mwe2NcUAMJ1yLKZJumQQjC.jpg")
                .build()
        when:
        def execute = wykop.sendMessage(IntegrationData.secondAccountLogin, newMessage).execute()
        then:
        execute.body() == "tresc"
        execute.embed().isPresent()
    }

    @TwoAccounts
    def "should send message with body and file media"() {
        def newMessage = new NewMessage.Builder()
                .withBody("tresc")
                .withMedia(new File("src/test/resources/white.jpg"))
                .build()
        when:
        def execute = wykop.sendMessage(IntegrationData.secondAccountLogin, newMessage).execute()
        then:
        execute.body() == "tresc"
        execute.embed().isPresent()
    }

    @TwoAccounts
    def "should delete conversation"() {
        def newMessage = new NewMessage.Builder()
                .withBody("tresc")
                .withMedia(new File("src/test/resources/white.jpg"))
                .build()
        when:
        wykop.sendMessage(IntegrationData.secondAccountLogin, newMessage).execute()
        def deleted = wykop.deleteConversation(IntegrationData.secondAccountLogin).execute()
        then:
        deleted
    }

    def "should throw an exception when user does not exist"() {
        when:
        wykop.deleteConversation(UUID.randomUUID().toString()).execute()
        then:
        thrown UserNotFoundException
    }
}
