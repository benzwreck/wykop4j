package io.github.benzwreck.wykop4j.mapping

import io.github.benzwreck.wykop4j.WykopMappingTestObject
import io.github.benzwreck.wykop4j.entries.Comment
import io.github.benzwreck.wykop4j.entries.Entry
import io.github.benzwreck.wykop4j.entries.UserVote
import io.github.benzwreck.wykop4j.profiles.Color
import spock.lang.Specification

import java.time.LocalDateTime

class EntryMappingSpec extends Specification {
    WykopMappingTestObject mapper = new WykopMappingTestObject()

    def "should map json to Entry class"() {
        when:
        String json = SampleEntries.entryWithEmbedJson()
        def entry = mapper.map(json, Entry.class)
        then:
        entry.id() == 55333215
        entry.date() == LocalDateTime.of(2021, 02, 03, 21, 35, 36)
        entry.body() == "Już za chwilkę"
        def author = entry.author()
        author.login() == "quatroo96"
        author.color() == Color.ORANGE
        author.sex() == Optional.empty()
        author.avatar() == "https://www.wykop.pl/cdn/c3397992/avatar_def,q150.png"
        author.signupAt() == LocalDateTime.of(2018, 05, 21, 10, 04, 29)
        author.background() == Optional.empty()
        author.violationUrl() == "https://a2.wykop.pl/naruszenia/form/ot/profile/od/1502911/ud/5xOL/hs/6f8032abfc29cc66d25ce46e12488af58eb88708/rn/ZhasKJWMCf/"
        entry.blocked() == false
        entry.favorite() == false
        entry.voteCount() == 6
        entry.commentsCount() == 0
        entry.status() == "visible"
        def embed = entry.embed().get()
        embed.type() == "image"
        embed.url() == "https://www.wykop.pl/cdn/c3201142/comment_1612384536orZ67OolQ7sBDR7ndIFbr5.jpg"
        embed.source() == "1612384532815.gif"
        embed.preview() == "https://www.wykop.pl/cdn/c3201142/comment_1612384536orZ67OolQ7sBDR7ndIFbr5,w400.jpg"
        embed.plus18() == false
        embed.size() == "276KB"
        embed.animated() == true
        embed.ratio() == 0.982f
        entry.userVote() == UserVote.NOT_VOTED
        entry.app().get() == "Android"
        entry.violationUrl().get() == "https://a2.wykop.pl/naruszenia/form/ot/entry/od/55333215/ud/5xOL/hs/69ab7ab81eef21c24ac559438e9ed9125d73d5cb/rn/RZj76Aozhe/"
        entry.original() == "#jp2gmd #2137 #jp2 #papiez\nJuż za chwilkę"
        entry.url() == "https://www.wykop.pl/wpis/55333215/jp2gmd-2137-jp2-papiez-juz-za-chwilke/"
    }

    def "should map json to Entry class with survey and canVote"() {
        when:
        String json = SampleEntries.entryWithSurveyAndCanVoteJson()
        def entry = mapper.map(json, Entry.class)
        then:
        entry.canComment() == true
        def survey = entry.survey().get()
        survey.question() == "Ankieta"
        def answers = survey.answers()
        def firstAnswer = answers.get(0)
        firstAnswer.id() == 1
        firstAnswer.answer() == "odp1"
        firstAnswer.count() == 4
        firstAnswer.percentage() == Float.valueOf("36.36363636363637")
        def secondAnswer = answers.get(1)
        secondAnswer.id() == 2
        secondAnswer.answer() == "odp2"
        secondAnswer.count() == 7
        secondAnswer.percentage() == Float.valueOf("63.63636363636363")

    }

    def "should map json to Entry class with comments"(){
        when:
        String json = SampleEntries.entryWithComments()
        def entry = mapper.map(json, Entry.class)
        then:
        def comments = entry.comments()
        def firstComment = comments.get(0)
        firstComment.id() == 197386491
        firstComment.author().login() == "MarianoaItaliano"
        firstComment.entryId() == 55470093
        def secondComment = comments.get(1)
        secondComment.id() == 197386571
        secondComment.author().login() == "IdillaMZ"
        secondComment.entryId() == 55470093


    }
}
