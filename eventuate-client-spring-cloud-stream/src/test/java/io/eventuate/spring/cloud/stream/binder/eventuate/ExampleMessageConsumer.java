package io.eventuate.spring.cloud.stream.binder.eventuate;

import io.eventuate.example.banking.services.EventTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;

public class ExampleMessageConsumer {

  private Logger logger = LoggerFactory.getLogger(getClass());

  private EventTracker<Message<String>> events = EventTracker.create();

  public EventTracker<Message<String>> getEvents() {
    return events;
  }

  @StreamListener(Sink.INPUT)
  public void handleMessage(Message<String> message) {
    logger.info("Got message: " + message);
    events.onNext(message);
  }
}
