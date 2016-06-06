package io.eventuate.spring.cloud.stream.binder.eventuate;

import io.eventuate.javaclient.commonimpl.AggregateEvents;
import org.springframework.cloud.stream.binder.*;
import org.springframework.messaging.MessageChannel;

public class EventuateSubscriberBinder
  extends AbstractBinder<MessageChannel, ConsumerProperties, ProducerProperties> {

  private AggregateEvents eventuateAggregateStore;
  private EventuateExtendedBindingProperties extendedBindingProperties;

  public EventuateSubscriberBinder(EventuateBinderConfigurationProperties configurationProperties, AggregateEvents eventuateAggregateStore) {
    this.eventuateAggregateStore = eventuateAggregateStore;
  }

  @Override
  protected Binding<MessageChannel> doBindConsumer(String name, String group, MessageChannel inputTarget, ConsumerProperties properties) {
    EventuateConsumerProperties consumerProperties = extendedBindingProperties.getExtendedConsumerProperties(name);
    EventuateMessageProducer messageProducer = new EventuateMessageProducer(eventuateAggregateStore, name, consumerProperties.getAggregatesAndEvents());
    messageProducer.setOutputChannel(inputTarget);
    messageProducer.setBeanFactory(this.getBeanFactory());
    messageProducer.afterPropertiesSet();
    return new DefaultBinding<>(name, group, inputTarget, messageProducer);
  }

  @Override
  protected Binding<MessageChannel> doBindProducer(String name, MessageChannel outboundBindTarget, ProducerProperties properties) {
    throw new UnsupportedOperationException();
  }

  public void setExtendedBindingProperties(EventuateExtendedBindingProperties extendedBindingProperties) {
    this.extendedBindingProperties = extendedBindingProperties;
  }

  public EventuateExtendedBindingProperties getExtendedBindingProperties() {
    return extendedBindingProperties;
  }
}
