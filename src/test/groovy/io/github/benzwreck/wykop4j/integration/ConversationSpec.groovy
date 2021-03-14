package io.github.benzwreck.wykop4j.integration

import io.github.benzwreck.wykop4j.IntegrationData
import io.github.benzwreck.wykop4j.IntegrationWykopClient
import io.github.benzwreck.wykop4j.TwoAccounts
import io.github.benzwreck.wykop4j.WykopClient
import io.github.benzwreck.wykop4j.conversations.NewMessage
import io.github.benzwreck.wykop4j.exceptions.UserNotFoundException
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class ConversationSpec extends Specification {
    static WykopClient wykop = IntegrationWykopClient.getInstance()
    static String nonexistentLogin = UUID.randomUUID().toString()
    @Shared
    NewMessage newMessage = new NewMessage.Builder()
            .withBody("tresc")
            .withMedia(new File("src/test/resources/white.jpg"))
            .build()

    @Unroll
    def "should not thrown an exception"() {
        when:
        result.execute()
        then:
        noExceptionThrown()
        where:
        result                                                 | _
        wykop.conversationsList()                              | _
        wykop.conversation(IntegrationData.secondAccountLogin) | _
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
        when:
        def execute = wykop.sendMessage(IntegrationData.secondAccountLogin, newMessage).execute()
        then:
        execute.body() == "tresc"
        execute.embed().isPresent()
    }

    @TwoAccounts
    def "should delete conversation"() {
        when:
        wykop.sendMessage(IntegrationData.secondAccountLogin, newMessage).execute()
        def deleted = wykop.deleteConversation(IntegrationData.secondAccountLogin).execute()
        then:
        deleted
    }

    @Unroll
    def "should throw an exception when user does not exist"() {
        when:
        result.execute()
        then:
        thrown(UserNotFoundException)
        where:
        result                                          | _
        wykop.deleteConversation(nonexistentLogin)      | _
        wykop.sendMessage(nonexistentLogin, newMessage) | _
    }
}
