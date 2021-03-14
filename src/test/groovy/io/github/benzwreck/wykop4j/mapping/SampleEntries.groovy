package io.github.benzwreck.wykop4j.mapping

class SampleEntries {
    String entryWithEmbedJson = '''
{
  "data": {
    "id": 55333215,
    "date": "2021-02-03 21:35:36",
    "body": "Już za chwilkę",
    "author": {
      "login": "quatroo96",
      "color": 1,
      "avatar": "https://www.wykop.pl/cdn/c3397992/avatar_def,q150.png",
      "signup_at": "2018-05-21 10:04:29",
      "violation_url": "https://a2.wykop.pl/naruszenia/form/ot/profile/od/1502911/ud/5xOL/hs/6f8032abfc29cc66d25ce46e12488af58eb88708/rn/ZhasKJWMCf/"
    },
    "blocked": false,
    "favorite": false,
    "vote_count": 6,
    "comments_count": 0,
    "status": "visible",
    "embed": {
      "type": "image",
      "url": "https://www.wykop.pl/cdn/c3201142/comment_1612384536orZ67OolQ7sBDR7ndIFbr5.jpg",
      "source": "1612384532815.gif",
      "preview": "https://www.wykop.pl/cdn/c3201142/comment_1612384536orZ67OolQ7sBDR7ndIFbr5,w400.jpg",
      "plus18": false,
      "size": "276KB",
      "animated": true,
      "ratio": 0.982
    },
    "user_vote": 0,
    "app": "Android",
    "violation_url": "https://a2.wykop.pl/naruszenia/form/ot/entry/od/55333215/ud/5xOL/hs/69ab7ab81eef21c24ac559438e9ed9125d73d5cb/rn/RZj76Aozhe/",
    "original": "#jp2gmd #2137 #jp2 #papiez\\nJuż za chwilkę",
    "url": "https://www.wykop.pl/wpis/55333215/jp2gmd-2137-jp2-papiez-juz-za-chwilke/"
  }
}'''
    String entryWithSurveyAndCanVoteJson = '''
{
  "data": {
    "id": 54760047,
    "date": "2021-01-08 02:38:08",
    "body": "ankieta",
    "author": {
      "login": "ZycieJestNobelon",
      "color": 1,
      "sex": "male",
      "avatar": "https://www.wykop.pl/cdn/c3397992/ZycieJestNobelon_2WW7g3RXYw,q150.jpg",
      "signup_at": "2017-12-31 19:36:07",
      "background": "https://www.wykop.pl/cdn/c3397992/profile_background-ZycieJestNobelon_RnfuZp4Nya,w500.jpg"
    },
    "blocked": false,
    "favorite": false,
    "vote_count": 0,
    "comments_count": 0,
    "status": "visible",
    "survey": {
      "question": "Ankieta",
      "answers": [
        {
          "id": 1,
          "answer": "odp1",
          "count": 4,
          "percentage": 36.36363636363637
        },
        {
          "id": 2,
          "answer": "odp2",
          "count": 7,
          "percentage": 63.63636363636363
        }
      ]
    },
    "can_comment": true,
    "user_vote": 0,
    "original": "ankieta",
    "url": "https://www.wykop.pl/wpis/54760047/ankieta/"
  }
}'''
    String entryWithComments = '''
{
  "data": {
    "id": 55470093,
    "date": "2021-02-10 15:57:12",
    "body": "Dziś mamy dzień bez kłamstw w mediach\\n\\n#mediabezwyboru #media #protest",
    "author": {
      "login": "putinex",
      "color": 1,
      "sex": "female",
      "avatar": "https://www.wykop.pl/cdn/c3397992/putinex_aDwrOepJU6,q150.jpg",
      "signup_at": "2018-03-22 23:33:23",
      "background": "https://www.wykop.pl/cdn/c3397992/profile_background-putinex_ST3tzuW3XQ,w500.jpg",
      "violation_url": "https://a2.wykop.pl/naruszenia/form/ot/profile/od/1455059/ud/5xOL/hs/f873240903548be27394593bbf38cb8ef564d14c/rn/DGStieMObj/"
    },
    "blocked": false,
    "favorite": false,
    "vote_count": 1,
    "comments_count": 2,
    "comments": [
      {
        "id": 197386491,
        "author": {
          "login": "MarianoaItaliano",
          "color": 2,
          "sex": "male",
          "avatar": "https://www.wykop.pl/cdn/c3397992/MarianoaItaliano_jc1zPA5hda,q150.jpg",
          "signup_at": "2014-02-17 00:50:09",
          "background": "https://www.wykop.pl/cdn/c3397992/profile_background-MarianoaItaliano_4kJAcrbX9z,w500.jpg",
          "violation_url": "https://a2.wykop.pl/naruszenia/form/ot/profile/od/607382/ud/5xOL/hs/f57f865740c91c6458829efe2f1a764986d04a3f/rn/qfHCbrNxYk/"
        },
        "date": "2021-02-10 15:57:38",
        "body": "@putinex: 60 groszy cyk",
        "blocked": false,
        "favorite": false,
        "vote_count": 3,
        "status": "public",
        "user_vote": 0,
        "violation_url": "https://a2.wykop.pl/naruszenia/form/ot/entrycomment/od/197386491/ud/5xOL/hs/bb01f2d0d9350866f60dd58f266fe9bc4b9ec4f5/rn/ZdgwDLCcBW/",
        "original": "@putinex: 60 groszy cyk",
        "entry_id": 55470093
      },
      {
        "id": 197386571,
        "author": {
          "login": "IdillaMZ",
          "color": 1,
          "sex": "male",
          "avatar": "https://www.wykop.pl/cdn/c3397992/IdillaMZ_hWoooCpMBw,q150.jpg",
          "signup_at": "2015-12-10 21:17:50",
          "background": "https://www.wykop.pl/cdn/c3397992/profile_background-IdillaMZ_lh7Ze02fck,w500.jpg",
          "violation_url": "https://a2.wykop.pl/naruszenia/form/ot/profile/od/937935/ud/5xOL/hs/11a149e62c1222163c51af1d06d66e116069f9aa/rn/hq7nylZaIk/"
        },
        "date": "2021-02-10 15:58:31",
        "body": "@MarianoaItaliano: Tak nisko się cenisz?",
        "blocked": false,
        "favorite": false,
        "vote_count": 0,
        "status": "public",
        "user_vote": 0,
        "violation_url": "https://a2.wykop.pl/naruszenia/form/ot/entrycomment/od/197386571/ud/5xOL/hs/50e1de10cb42fa226ff370092129f5f3f36f01cf/rn/vvPrEcGWOk/",
        "original": "@MarianoaItaliano: Tak nisko się cenisz?",
        "entry_id": 55470093
      }
    ],
    "status": "visible",
    "can_comment": true,
    "user_vote": 0,
    "violation_url": "https://a2.wykop.pl/naruszenia/form/ot/entry/od/55470093/ud/5xOL/hs/106fff878bb14fdd72786fc080a5f4ae93363164/rn/EikGHyt0A6/",
    "original": "Dziś mamy dzień bez kłamstw w mediach\\r\\n\\r\\n#mediabezwyboru #media #protest",
    "url": "https://www.wykop.pl/wpis/55470093/dzis-mamy-dzien-bez-klamstw-w-mediach-mediabezwybo/"
  }
}'''
}
