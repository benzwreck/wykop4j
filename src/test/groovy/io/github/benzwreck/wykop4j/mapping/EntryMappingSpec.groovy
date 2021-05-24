package io.github.benzwreck.wykop4j.mapping

import io.github.benzwreck.wykop4j.WykopMappingTestObject
import io.github.benzwreck.wykop4j.entries.Entry
import io.github.benzwreck.wykop4j.entries.EntryWithComments
import io.github.benzwreck.wykop4j.shared.UserVote
import io.github.benzwreck.wykop4j.profiles.Color
import spock.lang.Specification

import java.time.LocalDateTime

class EntryMappingSpec extends Specification {
    WykopMappingTestObject mapper = new WykopMappingTestObject()
    SampleEntries sampleEntries = new SampleEntries()

    def "should map json to Entry class"() {
        when:
        def entry = mapper.map(sampleEntries.entryWithEmbedJson, Entry)
        then:
        with entry, {
            id() == 55333215
            date() == LocalDateTime.of(2021, 02, 03, 21, 35, 36)
            body().get() == "Już za chwilkę"
            with author(), {
                login() == "quatroo96"
                color() == Color.ORANGE
                sex() == Optional.empty()
                avatar() == "https://www.wykop.pl/cdn/c3397992/avatar_def,q150.png"
            }
            blocked() == false
            favorite() == false
            voteCount() == 6
            commentsCount() == 0
            status() == "visible"
            with embed().get(), {
                type() == "image"
                url() == "https://www.wykop.pl/cdn/c3201142/comment_1612384536orZ67OolQ7sBDR7ndIFbr5.jpg"
                source() == "1612384532815.gif"
                preview() == "https://www.wykop.pl/cdn/c3201142/comment_1612384536orZ67OolQ7sBDR7ndIFbr5,w400.jpg"
                plus18() == false
                size() == "276KB"
                animated() == true
                ratio() == 0.982f
            }
            userVote() == UserVote.NOT_VOTED
            app().get() == "Android"
            violationUrl().get() == "https://a2.wykop.pl/naruszenia/form/ot/entry/od/55333215/ud/5xOL/hs/69ab7ab81eef21c24ac559438e9ed9125d73d5cb/rn/RZj76Aozhe/"
            original() == "#jp2gmd #2137 #jp2 #papiez\nJuż za chwilkę"
            url() == "https://www.wykop.pl/wpis/55333215/jp2gmd-2137-jp2-papiez-juz-za-chwilke/"
        }
    }

    def "should map json to Entry class with survey and canVote"() {
        when:
        def entry = mapper.map(sampleEntries.entryWithSurveyAndCanVoteJson, Entry)
        then:
        with entry, {
            canComment() == true
            with survey().get(), {
                question() == "Ankieta"
                with answers(), {
                    with get(0), {
                        id() == 1
                        answer() == "odp1"
                        count() == 4
                        percentage() == Float.valueOf("36.36363636363637")
                    }
                    with get(1), {
                        id() == 2
                        answer() == "odp2"
                        count() == 7
                        percentage() == Float.valueOf("63.63636363636363")
                    }
                }

            }
        }
    }

    def "should map json to Entry class with comments"() {
        when:
        def entry = mapper.map(sampleEntries.entryWithComments, EntryWithComments)
        then:
        with entry.comments(), {
            with get(0), {
                id() == 197386491
                author().login() == "MarianoaItaliano"
                entryId() == 55470093
            }
            with get(1), {
                id() == 197386571
                author().login() == "IdillaMZ"
                entryId() == 55470093
            }
        }
    }
}
