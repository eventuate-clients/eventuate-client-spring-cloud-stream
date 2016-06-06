package io.eventuate.spring.cloud.stream.binder.eventuate;

import io.eventuate.EventuateAggregateStore;
import io.eventuate.SubscriberOptions;
import io.eventuate.javaclient.commonimpl.AggregateEvents;
import io.eventuate.javaclient.commonimpl.SerializedEvent;
import org.springframework.integration.endpoint.MessageProducerSupport;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class EventuateMessageProducer extends MessageProducerSupport {


  private AggregateEvents eventuateAggregateStore;
  private String subscriberId;
  private Map<String, Set<String>> aggregatesAndEvents;

  public EventuateMessageProducer(AggregateEvents eventuateAggregateStore, String subscriberId, Map<String, Set<String>> aggregatesAndEvents) {
    this.eventuateAggregateStore = eventuateAggregateStore;
    this.subscriberId = subscriberId;
    this.aggregatesAndEvents = aggregatesAndEvents;
  }

  @Override
  protected void onInit() {
    super.onInit();
    // TODO - should handle failure/timeout
    eventuateAggregateStore.subscribe(subscriberId,
            aggregatesAndEvents,
            SubscriberOptions.DEFAULTS,
            se -> {
              sendMessage(toMessage(se));
              return CompletableFuture.completedFuture(null);
            }
    );
  }

  private Message<String> toMessage(SerializedEvent se) {
    return MessageBuilder
            .withPayload(se.getEventData())
            .setHeader("eventId", se.getId().asString())
            .setHeader("eventType", se.getEventType())
            .setHeader("entityId", se.getEntityId())
            .setHeader("entityType", se.getEntityType())
            .setHeader("swimLane", se.getSwimLane())
            .setHeader("offset", se.getOffset())
            .setHeader("eventContext", se.getEventContext().getEventToken())
            .build();
  }

}
