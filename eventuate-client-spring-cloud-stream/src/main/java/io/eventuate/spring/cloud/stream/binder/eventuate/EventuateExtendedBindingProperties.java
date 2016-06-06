package io.eventuate.spring.cloud.stream.binder.eventuate;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.stream.binder.ExtendedBindingProperties;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties("eventuate.spring.cloud.stream")
public class EventuateExtendedBindingProperties implements ExtendedBindingProperties<EventuateConsumerProperties, EventuateProducerProperties> {
  private Map<String, EventuateBindingProperties> bindings = new HashMap<>();

  public Map<String, EventuateBindingProperties> getBindings() {
    return bindings;
  }

  public void setBindings(Map<String, EventuateBindingProperties> bindings) {
    this.bindings = bindings;
  }

  @Override
  public EventuateConsumerProperties getExtendedConsumerProperties(String channelName) {
    if (bindings.containsKey(channelName) && bindings.get(channelName).getConsumer() != null) {
      return bindings.get(channelName).getConsumer();
    }
    else {
      return new EventuateConsumerProperties();
    }
  }

  @Override
  public EventuateProducerProperties getExtendedProducerProperties(String channelName) {
    if (bindings.containsKey(channelName) && bindings.get(channelName).getProducer() != null) {
      return bindings.get(channelName).getProducer();
    }
    else {
      return new EventuateProducerProperties();
    }
  }
}
