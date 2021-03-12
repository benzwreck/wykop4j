package io.github.benzwreck.wykop4j.mapping

import com.fasterxml.jackson.core.type.TypeReference
import io.github.benzwreck.wykop4j.WykopMappingTestObject
import io.github.benzwreck.wykop4j.links.Link
import io.github.benzwreck.wykop4j.profiles.Color
import io.github.benzwreck.wykop4j.profiles.Sex
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
}
