package io.github.benzwreck.wykop4j.mapping

import com.fasterxml.jackson.core.type.TypeReference
import io.github.benzwreck.wykop4j.WykopMappingTestObject
import io.github.benzwreck.wykop4j.links.Link
import io.github.benzwreck.wykop4j.links.LinkComment
import io.github.benzwreck.wykop4j.links.LinkDraft
import io.github.benzwreck.wykop4j.links.LinkImage
import io.github.benzwreck.wykop4j.links.RelatedLinkVoteData
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
            violationUrl() == "https://a2.wykop.pl/naruszenia/form/ot/comment/od/83090897/ud/5xOL/hs/fadd4e43958a10f0ea4cec36afbf3f5074d44a0c/rn/FyAEGlP498/"
            voteCountMinus() == -20
            original() == "@beconase: bardzo dziekuje :). Na szczescie, Najwazniejsi pamietaja :)"
        }

    }

    def "should map json to related link vote data"() {
        given:
        RelatedLinkVoteData relatedLinkVoteData = mapper.map(sampleLinks.relatedLinkVoteData, RelatedLinkVoteData)
        expect:
        relatedLinkVoteData.voteCount() == 62
    }

    def "should map json to link draft"() {
        given:
        LinkDraft linkDraft = mapper.map(sampleLinks.linkDraft, LinkDraft)
        expect:
        with linkDraft, {
            key() == "595878775a67414642685578"
            title().get() == "Best JSON Formatter and JSON Validator: Online JSON Formatter"
            description().get() == "Online JSON Formatter and JSON Validator will format JSON data, and helps to validate, convert JSON to XML, JSON to CSV. Save and Share JSON"
            sourceUrl() == "https://jsonformatter.org/"
            with duplicates().get(0), {
                id() == 100453
                title() == "Water Powered Cars - Convert Your Car To Run On Water And Save Money"
                description() == "Water Powered Cars - Convert Your Car To Run On Water And Save Money"
                tags() == "#technologia #nowetechnologie #water #powered #cars"
                sourceUrl() == "http://ezinearticles.com/?Water-Powered-Cars---Convert-Your-Car-To-Run-On-Water-And-Save-Money&amp;id=1081759"
                voteCount() == 0
                buryCount() == 0
                commentsCount() == 0
                relatedCount() == 0
                date() == LocalDateTime.of(2008, 10, 07, 07, 03, 00)
                with author(), {
                    login() == "biten98"
                    color() == Color.BANNED
                    avatar() == "https://www.wykop.pl/cdn/c3397992/avatar_def,q150.png"
                    sex().isEmpty()
                }
                plus18() == false
                status() == "removed"
                canVote() == false
                isHot() == false
                archived() == true
                userFavorite() == false
                userObserve() == false
                hasOwnContent() == false
                url() == "https://www.wykop.pl/link/100453/water-powered-cars-convert-your-car-to-run-on-water-and-save-money/"
                violationUrl() == "https://a2.wykop.pl/naruszenia/form/ot/link/od/100453/ud/9KdP/hs/5dcff10519b9453f52d5faca0e4b9fdb104a0a3c/rn/IAiK7aJ2I0/"

            }
        }
    }

    def "should map json to image draft"() {
        given:
        LinkImage preparedImage = mapper.map(sampleLinks.preparedImage, LinkImage)
        expect:
        with preparedImage, {
            key() == "595878775a67414b43784578"
            type() == "image/jpeg"
            previewUrl() == "https://preview.pl/prev.jpg"
            sourceUrl() == "https://art-madam.pl/zdjecie/nowoczesny-obraz-do-salonu-drukowany-na-plotnie,swsbnckstxdjnwbh.jpg"
        }
    }
}
