package io.github.benzwreck.wykop4j.mapping

import com.fasterxml.jackson.core.type.TypeReference
import io.github.benzwreck.wykop4j.WykopMappingTestObject
import io.github.benzwreck.wykop4j.links.Link
import io.github.benzwreck.wykop4j.links.LinkComment
import io.github.benzwreck.wykop4j.profiles.Color
import io.github.benzwreck.wykop4j.profiles.Sex
import io.github.benzwreck.wykop4j.shared.UserVote
import spock.lang.Specification

import java.time.LocalDateTime

class LinkMappingSpec extends Specification {
    WykopMappingTestObject mapper = new WykopMappingTestObject()
    SampleLinks sampleLinks = new SampleLinks()

    def "should map json to link"() {
        given:
        Link link = mapper.map(sampleLinks.singleLink, Link)
        expect:
        with link, {
            id() == 5347365
            title() == "77-latek zaatakowany przy wypłacie pieniędzy. Obronił się własnymi..."
            description() == "Starszy mężczyzna zostaje zaatakowany po wypłacie pieniędzy z bankomatu. Napastnik szybko przekonuje się, że popełnił błąd.    \tDo 77 latka podchodzi mężczyzna który go atakuje, żąda got&oac..."
            tags() == "#europa #boks #napad"
            sourceUrl() == "https://wtk.pl/news/55728-77-latek-zaatakowany-przy-wyplacie-pieniedzy-obronil-sie-wlasnymi-piesciami"
            voteCount() == 0
            buryCount() == 4
            commentsCount() == 3
            relatedCount() == 0
            date() == LocalDateTime.of(2020, 2, 22, 16, 38, 48)
            with author(), {
                login() == "m__b"
                color() == Color.ADMIN
                sex().get() == Sex.MALE
                avatar() == "https://www.wykop.pl/cdn/c3397992/avatar_def,q150.png"
            }
            preview() == "https://www.wykop.pl/cdn/c3397993/link_15823858618lCllKQzrRuZL5Z1pl3RPm,w104h74.jpg"
            plus18() == false
            status() == "rejected"
            canVote() == false
            isHot() == false
            userFavorite() == false
            userObserve() == false
            with info().get(), {
                color() == Link.Info.Color.RED
                body() == "Duplikat: https://www.wykop.pl/link/5344019"
            }
            hasOwnContent() == false
            "https://www.wykop.pl/link/5347365/77-latek-zaatakowany-przy-wyplacie-pieniedzy-obronil-sie-wlasnymi/"
            violationUrl() == "https://a2.wykop.pl/naruszenia/form/ot/link/od/5347365/ud/5xOL/hs/2d807ee2f8ffc4779742d1666769578c7f20e6c2/rn/K6HXl67FuS/"

        }
    }

    def "should map json to list of three links"() {
        given:
        List<Link> links = mapper.map(sampleLinks.listOfThreeLinks, new TypeReference<List<Link>>() {})
        expect:
        links.size() == 3
    }

    def "should map json to link's comment"() {
        given:
        LinkComment linkComment = mapper.map(sampleLinks.singleLinkComment, LinkComment)
        expect:
        with linkComment, {
            id() == 83090897
            date() == LocalDateTime.of(2020, 10, 19, 18, 00, 11)
            with author(), {
                login() == "m__b"
                color() == Color.ADMIN
                sex().get() == Sex.MALE
                avatar() == "https://www.wykop.pl/cdn/c3397992/avatar_def,q150.png"
            }
            voteCount() == -12
            voteCountPlus() == 8
            body() == "@beconase: bardzo dziekuje :). Na szczescie, Najwazniejsi pamietaja :)"
            parentId() == 83090327
            canVote() == true
            userVote() == UserVote.NOT_VOTED
            blocked() == false
            favorite() == false
            linkId() == 5750693
            with link(), {
                id() == 5750693
                title() == "Zasady działań moderacyjnych - standardy moderacji"
                description() == "Wykopowicze, zdajemy sobie sprawę, że podejmowane przez nas działania moderacyjne nie zawsze spotykają się z Waszą aprobatą.  Wiemy też, że zdarza się, iż nie rozumiecie dlaczego zgłoszona przez Was treść została lub nie została przez nas..."
                tags() == "#wykop #moderacja #zasady #polska"
                sourceUrl() == "http://www.wykop.pl/artykul/5750693/zasady-dzialan-moderacyjnych-standardy-moderacji/"
                voteCount() == 1164
                buryCount() == 0
                commentsCount() == 945
                relatedCount() == 5
                date() == LocalDateTime.of(2020, 10, 19, 16, 23, 02)
                with author(), {
                    login() == "wykop"
                    color() == Color.ADMIN
                    sex() == Optional.empty()
                    avatar() == "https://www.wykop.pl/cdn/c3397992/wykop_U5laqyXEOf,q150.jpg"
                }
                preview() == "https://www.wykop.pl/cdn/c3397993/link_1603117815m5OxIJrwkSgutxxYYW5P4t,w104h74.jpg"
                plus18() == false
                status() == "promoted"
                canVote() == true
                isHot() == true
                userFavorite() == false
                userObserve() == false
                with info().get(), {
                    color() == Link.Info.Color.YELLOW
                    body() == "Wykopowicze, dziękujemy za Wasze zaangażowanie, liczne komentarze, sugestie i opinie w temacie! \r\nW konsultacji z Wami przygotowaliśmy zbiór zasad oraz wytycznych, w których określiliśmy jakie treści, działania i aktywności w serwisie nie są akceptowane na Wykopie. Standardy moderacji dostępne będą publicznie pod adresem: https://www.wykop.pl/pomoc/standardy-moderacji/"
                }
                hasOwnContent() == true
                url() == "https://www.wykop.pl/link/5750693/zasady-dzialan-moderacyjnych-standardy-moderacji/"
                violationUrl() == "https://a2.wykop.pl/naruszenia/form/ot/link/od/5750693/ud/5xOL/hs/df5e78196a33ccffe400cd2fa9c05e815b4371a2/rn/79U7l1LE1b/"
            }
            violationUrl() == "https://a2.wykop.pl/naruszenia/form/ot/comment/od/83090897/ud/5xOL/hs/fadd4e43958a10f0ea4cec36afbf3f5074d44a0c/rn/FyAEGlP498/"
            voteCountMinus() == -20
            original() == "@beconase: bardzo dziekuje :). Na szczescie, Najwazniejsi pamietaja :)"
        }

    }
}
