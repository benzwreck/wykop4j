# Wykop4j
Simple Java Wykop API client.<br>
Currently, only single user login available.
---
#### External dependencies
* Okhttp
# Testing
In order to run tests you have to create `IntegrationWykopClient` class and provide `TestWykopClient.getInstance()`
returning `WykopClient` instance with your `UserCredentials` and `ApplicationCredentials`.

