# Wykop4j
Simple Java Wykop API client.
#### External dependencies
* Okhttp
* Jackson
#### Testing
In order to run tests you have to create `IntegrationWykopClient` class and provide `TestWykopClient.getInstance()`
returning `WykopClient` instance with your `UserCredentials` and `ApplicationCredentials`.
