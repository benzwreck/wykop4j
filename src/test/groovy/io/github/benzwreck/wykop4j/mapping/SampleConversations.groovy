package io.github.benzwreck.wykop4j.mapping

class SampleConversations {
    String conversationList = '''
{
  "data": [
    {
      "last_update": "2021-01-07 12:39:47",
      "receiver": {
        "login": "Mama",
        "color": 1,
        "sex": "female",
        "avatar": "https://www.wykop.pl/cdn/c3397992/mama_tatat,q150.jpg"
      },
      "status": "read"
    },
    {
      "last_update": "2021-01-06 22:55:08",
      "receiver": {
        "login": "Tata",
        "color": 1,
        "sex": "male",
        "avatar": "https://www.wykop.pl/cdn/c3397992/avatar_def,q150.png"
      },
      "status": "read"
    }
  ]
}'''
    String conversation = '''
{
  "data": [
    {
      "id": 111111111,
      "date": "2021-01-01 11:11:11",
      "body": "Dzień dobry!",
      "status": "read",
      "direction": "received"
    },
    {
      "id": 222222222,
      "date": "2020-01-01 21:13:22",
      "body": "Do widzenia!",
      "status": "read",
      "direction": "sended"
    }
  ]
}'''
}
