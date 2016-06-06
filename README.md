# Eventuate Binder for Spring Cloud Stream

The Eventuate Binder for [Spring Cloud Stream](https://cloud.spring.io/spring-cloud-stream/) enables a Spring Cloud Stream application to consume events from the Eventuate event store.
[Eventuate](http://eventuate.io/) is a platform for simplifying the development of transactional microservices.
It works in conjunction with frameworks such as Spring Boot to provide a programming model that is based on event sourcing and CQRS.
You can try the SaaS version of Eventuate for free by [signing up here](https://signup.eventuate.io/).

# Using the Eventuate Binder

Consider, for example, this simple Spring Cloud Stream application.
It consumes messages from a stream and prints them.

```
@SpringBootApplication
@EnableBinding(Sink.class)
public class EventuateStreamDemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(EventuateStreamDemoApplication.class, args);
  }

  @Bean
  public ExampleMessageConsumer exampleMessageConsumer() {
    return new ExampleMessageConsumer();
  }

}
```

```
public class ExampleMessageConsumer {

  @StreamListener(Sink.INPUT)
  public void handleMessage(Message<String> message) {
    System.out.println("Got message: " + message);
  }
}
```

To consume events published by Eventuate you do need to the following.

## Configure Maven/Gradle dependencies

Maven:

```
<repositories>
  <repository>
      <id>eventuate-release</id>
      <url>http://mavenrepo.eventuate.io/release</url>
      <snapshots>
          <enabled>false</enabled>
      </snapshots>
  </repository>
</repositories>

<dependency>
  <groupId>io.eventuate.client.spring.cloud.stream</groupId>
  <artifactId>eventuate-client-spring-cloud-stream</artifactId>
  <version>0.1.0.RELEASE</version>
</dependency>
<dependency>
  <groupId>io.eventuate.client.java</groupId>
  <artifactId>eventuate-client-java-http-stomp-spring</artifactId>
  <version>0.1/version>
</dependency>
```

Gradle:

```
repositories {
    ...
    maven { url "http://mavenrepo.eventuate.io/release" }
}
...
dependencies {
  ...
  compile "io.eventuate.client.spring.cloud.stream:eventuate-client-spring-cloud-stream:0.1.0.RELEASE"
  compile "io.eventuate.client.java:eventuate-client-java-http-stomp-spring:0.1"
}

```

## Configure the Eventuate client

In order for the Eventuate client to connect to the Eventuate event store you need to configure the following properties: `eventuate.api.key.id`, `eventuate.api.key.secret`.
These are the credentials that you received when you [signed up for Eventuate](https://signup.eventuate.io/).

You can supply these properties using the usual Spring Boot mechanism. e.g. in an `application.properties` file or by setting environment variables.

## Configure the Spring Cloud Stream Eventuate Consume

Since the example application shown above reads from a consumer called `input`, we need to configure a consumer with this name.
The Eventuate Binder has a property called `aggregatesAndEvents`, which is a map from entity type to a set of event names.
Here is an example configuration that consumes events published by the `Account` aggregate for the [Money Transfer application](https://github.com/cer/event-sourcing-examples/tree/master/java-spring)

```
  eventuate.spring.cloud.stream.bindings.input.consumer.\
    aggregatesAndEvents[net.chrisrichardson.eventstore.javaexamples.banking.backend.commandside.accounts.Account]=\
    net.chrisrichardson.eventstore.javaexamples.banking.backend.common.accounts.AccountOpenedEvent,\ net.chrisrichardson.eventstore.javaexamples.banking.backend.common.accounts.AccountDebitedEvent,\
     net.chrisrichardson.eventstore.javaexamples.banking.backend.common.accounts.AccountCreditedEvent
```
