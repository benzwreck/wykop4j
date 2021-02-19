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

    def "should map json of array with two direct notifications"() {
        when:
        def notifications = mapper.map(SampleNotifications.directNotificationsJson, new TypeReference<List<Notification>>() {
        })
        then:
        def first = notifications.get(0)
        first.id() == 1000000000L
        def firstAuthor = first.author()
        firstAuthor.login() == "nobodycares"
        firstAuthor.color() == Color.ORANGE
        firstAuthor.sex().get() == Sex.FEMALE
        firstAuthor.avatar() == "https://www.wykop.pl/cdn/c331111/blabla,q150.jpg"
        first.date() == LocalDateTime.of(2021, 2, 18, 21, 16, 32)
        first.body() == "nobodycares napisała do Ciebie w komentarzu do \"/Jestem tu\"."
        first.type() == "entry_comment_directed"
        first.itemId() == 88888888
        first.subitemId() == 123123123
        first.url() == "https://www.wykop.pl/wpis/88888888/jestem-tu-/#comment-123123123"
        first.isNew() == true

        def second = notifications.get(1)
        second.id() == 2222222222L
        def secondAuthor = second.author()
        secondAuthor.login() == "ziomeczek"
        secondAuthor.color() == Color.GREEN
        secondAuthor.sex().get() == Sex.MALE
        secondAuthor.avatar() == "https://www.wykop.pl/cdn/c111111/ziomeczek,q150.jpg"
        second.date() == LocalDateTime.of(2021, 2, 18, 9, 42, 36)
        second.body() == "ziomeczek napisał do Ciebie w komentarzu do \"Jestem tam\"."
        second.type() == "entry_comment_directed"
        second.itemId() == 55555555
        second.subitemId() == 321321321
        second.url() == "https://www.wykop.pl/wpis/55555555/notifications-index-gt-jestem-tam-/#comment-321321321"
        second.isNew() == false
    }

    def "should map json of empty array to empty list"(){
        when:
        def notifications = mapper.map(SampleNotifications.emptyDirectNotificationsJson, new TypeReference<List<Notification>>() {})
        then:
        notifications.isEmpty()
    }

    def "should return zero notification count"(){
        when:
        def notificationCount = mapper.map(SampleNotifications.zeroNotificationCount, Integer.class)
        then:
        notificationCount == 0
    }
}
