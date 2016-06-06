package io.eventuate.spring.cloud.stream.binder.eventuate;

import io.eventuate.EventuateAggregateStore;
import io.eventuate.javaclient.commonimpl.AggregateEvents;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.binder.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ EventuateBinderConfigurationProperties.class, EventuateExtendedBindingProperties.class })
public class EventuateBinderConfiguration {

  @Autowired
  private EventuateBinderConfigurationProperties configurationProperties;

  @Autowired
  private EventuateExtendedBindingProperties eventuateExtendedBindingProperties;

  @Bean
  public Binder eventuateSubscriberBinder(AggregateEvents eventuateAggregateStore) {
    EventuateSubscriberBinder binder = new EventuateSubscriberBinder(configurationProperties, eventuateAggregateStore);
    binder.setExtendedBindingProperties(eventuateExtendedBindingProperties);
    return binder;
  }
}
