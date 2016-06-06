package io.eventuate.spring.cloud.stream.binder.eventuate;

public class EventuateBindingProperties {
  private EventuateConsumerProperties consumer = new EventuateConsumerProperties();

  private EventuateProducerProperties producer = new EventuateProducerProperties();

  public EventuateConsumerProperties getConsumer() {
    return consumer;
  }

  public void setConsumer(EventuateConsumerProperties consumer) {
    this.consumer = consumer;
  }

  public EventuateProducerProperties getProducer() {
    return producer;
  }

  public void setProducer(EventuateProducerProperties producer) {
    this.producer = producer;
  }
}
