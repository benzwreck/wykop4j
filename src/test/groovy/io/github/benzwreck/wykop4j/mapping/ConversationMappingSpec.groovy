package io.github.benzwreck.wykop4j.mapping

import com.fasterxml.jackson.core.type.TypeReference
import io.github.benzwreck.wykop4j.WykopMappingTestObject
import io.github.benzwreck.wykop4j.conversations.ConversationInfo
import io.github.benzwreck.wykop4j.conversations.Message
import io.github.benzwreck.wykop4j.profiles.Color
import io.github.benzwreck.wykop4j.profiles.Sex
import spock.lang.Specification

import java.time.LocalDateTime

class ConversationMappingSpec extends Specification {
    WykopMappingTestObject mapper = new WykopMappingTestObject()

    def "should return array with two conversation's info"() {
        when:
        def conversationInfos = mapper.map(SampleConversations.conversationList, new TypeReference<List<ConversationInfo>>() {
        })
        then:
        def first = conversationInfos.get(0)
        first.lastUpdate() == LocalDateTime.of(2021, 1, 7, 12, 39, 47)
        def firstReceiver = first.receiver()
        firstReceiver.login() == "Mama"
        firstReceiver.color() == Color.ORANGE
        firstReceiver.sex().get() == Sex.FEMALE
        firstReceiver.avatar() == "https://www.wykop.pl/cdn/c3397992/mama_tatat,q150.jpg"
        first.status() == "read"

        def second = conversationInfos.get(1)
        second.lastUpdate() == LocalDateTime.of(2021, 1, 6, 22, 55, 8)
        def secondReceiver = second.receiver()
        secondReceiver.login() == "Tata"
        secondReceiver.color() == Color.ORANGE
        secondReceiver.sex().get() == Sex.MALE
        secondReceiver.avatar() == "https://www.wykop.pl/cdn/c3397992/avatar_def,q150.png"
        second.status() == "read"

    }

    def "should return array with two messages"() {
        when:
        def messages = mapper.map(SampleConversations.conversation, new TypeReference<List<Message>>() {})
        then:
        def first = messages.get(0)
        first.id() == 111111111
        first.date() == LocalDateTime.of(2021, 1, 1, 11, 11, 11)
        first.body() == "Dzień dobry!"
        first.status() == "read"
        first.direction() == Message.Direction.RECEIVED

        def second = messages.get(1)
        second.id() == 222222222
        second.date() == LocalDateTime.of(2020, 1, 1, 21, 13, 22)
        second.body() == "Do widzenia!"
        second.status() == "read"
        second.direction() == Message.Direction.SENDED
    }
}
