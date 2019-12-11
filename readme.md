# edc-client-java
The edc client is the java connector to get edc context documentation in HTML5 format.

This client read informations from defined url. The developer will be able to get contextual documentation content according to the keys and get the help url to display the documentation in the help client.

## edc Version

Current release is compatible with edc v3.0+

## How can I get the latest release?

You can pull it from the central Maven repositories:

### Maven
```xml
<dependency>
  <groupId>fr.techad</groupId>
  <artifactId>edc-client</artifactId>
  <version>3.0.0</version>
</dependency>
```

### Gradle
```groovy
    compile group: 'fr.techad', name: 'edc-client', version: '3.0.0'
```

## Configuration
### with Injection
Based on Guice, you need to include EdcClientModule and EdcPopoverModule in the injector creation.

```java
Injector injector = Guice.createInjector(new EdcClientModule());
```

To declare the server url, you have to inject ``EdcClient``

### with Singleton

If you develop your software without injection engine. You can use this tools with  ``EdcClientSingleton``

## How can I build this project ?

This project is based on *gradle*. You can install the artefacts in your local cache with:

``gradle clean PublishToMavenLocal``

## License

MIT [TECH'advantage](mailto:contact@tech-advantage.com)
