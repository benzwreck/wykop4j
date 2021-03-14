package io.github.benzwreck.wykop4j.mapping

import com.fasterxml.jackson.core.type.TypeReference
import io.github.benzwreck.wykop4j.WykopMappingTestObject
import io.github.benzwreck.wykop4j.notifications.Notification
import io.github.benzwreck.wykop4j.profiles.Color
import io.github.benzwreck.wykop4j.profiles.Sex
import spock.lang.Specification

import java.time.LocalDateTime

class NotificationMappingSpec extends Specification {
    WykopMappingTestObject mapper = new WykopMappingTestObject()
    SampleNotifications sampleNotifications = new SampleNotifications()

    def "should map json of array with two direct notifications"() {
        when:
        def notifications = mapper.map(sampleNotifications.directNotificationsJson, new TypeReference<List<Notification>>() {
        })
        then:
        with notifications, {
            with get(0), {
                id() == 1000000000L
                with author(), {
                    login() == "nobodycares"
                    color() == Color.ORANGE
                    sex().get() == Sex.FEMALE
                    avatar() == "https://www.wykop.pl/cdn/c331111/blabla,q150.jpg"
                }
                date() == LocalDateTime.of(2021, 2, 18, 21, 16, 32)
                body() == "nobodycares napisała do Ciebie w komentarzu do \"/Jestem tu\"."
                type() == "entry_comment_directed"
                itemId() == "88888888"
                subitemId() == "123123123"
                url() == "https://www.wykop.pl/wpis/88888888/jestem-tu-/#comment-123123123"
                isNew() == true
            }
            with get(1), {
                id() == 2222222222L
                with author(), {
                    login() == "ziomeczek"
                    color() == Color.GREEN
                    sex().get() == Sex.MALE
                    avatar() == "https://www.wykop.pl/cdn/c111111/ziomeczek,q150.jpg"
                }
                date() == LocalDateTime.of(2021, 2, 18, 9, 42, 36)
                body() == "ziomeczek napisał do Ciebie w komentarzu do \"Jestem tam\"."
                type() == "entry_comment_directed"
                itemId() == "55555555"
                subitemId() == "321321321"
                url() == "https://www.wykop.pl/wpis/55555555/notifications-index-gt-jestem-tam-/#comment-321321321"
                isNew() == false
            }
        }
    }

    def "should map json of array with two tags notifications"() {
        expect:
        with mapper.map(sampleNotifications.tagsNotificationsJson,
                new TypeReference<List<Notification>>() {
                }),
                {
                    get(0).type() == "entry_directed"
                    get(1).type() == "entry_directed"
                }
    }

    def "should map json of empty array to empty list"() {
        expect:
        mapper.map(sampleNotifications.emptyNotificationsJson,
                new TypeReference<List<Notification>>() {})
                .isEmpty()
    }

    def "should return zero notification count"() {
        expect:
        mapper.map(sampleNotifications.zeroNotificationCount, Integer) == 0
    }

    def "should return five notification count"() {
        expect:
        mapper.map(sampleNotifications.totalNotificationCount, Integer) == 5
    }
}
