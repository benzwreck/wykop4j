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
    SampleConversations sampleConversations = new SampleConversations()

    def "should return array with two conversation's info"() {
        when:
        def conversationInfos = mapper.map(sampleConversations.conversationList, new TypeReference<List<ConversationInfo>>() {
        })
        then:
        with conversationInfos, {
            with get(0), {
                lastUpdate() == LocalDateTime.of(2021, 1, 7, 12, 39, 47)
                with receiver(), {
                    login() == "Mama"
                    color() == Color.ORANGE
                    sex().get() == Sex.FEMALE
                    avatar() == "https://www.wykop.pl/cdn/c3397992/mama_tatat,q150.jpg"
                }
                status() == "read"
            }
            with get(1), {
                lastUpdate() == LocalDateTime.of(2021, 1, 6, 22, 55, 8)
                with receiver(), {
                    login() == "Tata"
                    color() == Color.ORANGE
                    sex().get() == Sex.MALE
                    avatar() == "https://www.wykop.pl/cdn/c3397992/avatar_def,q150.png"
                }
                status() == "read"
            }
        }
    }

    def "should return array with two messages"() {
        when:
        def messages = mapper.map(sampleConversations.conversation, new TypeReference<List<Message>>() {})
        then:
        with messages, {
            with get(0), {
                id() == 111111111
                date() == LocalDateTime.of(2021, 1, 1, 11, 11, 11)
                body() == "Dzień dobry!"
                status() == "read"
                direction() == Message.Direction.RECEIVED
            }
            with get(1), {
                id() == 222222222
                date() == LocalDateTime.of(2020, 1, 1, 21, 13, 22)
                body() == "Do widzenia!"
                status() == "read"
                direction() == Message.Direction.SENT
            }
        }
    }
}
