package io.eventuate.spring.cloud.stream.binder.eventuate;

import io.eventuate.example.banking.services.JavaIntegrationTestDomainConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import(JavaIntegrationTestDomainConfiguration.class)
@EnableBinding(Sink.class)
public class EventuateBinderIntegrationTestConfiguration {

  @Bean
  public ExampleMessageConsumer exampleMessageConsumer() {
    return new ExampleMessageConsumer();
  }

}
