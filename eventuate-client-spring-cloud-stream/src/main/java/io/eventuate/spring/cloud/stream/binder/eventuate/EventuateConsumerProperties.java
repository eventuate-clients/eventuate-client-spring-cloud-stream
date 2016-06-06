package io.eventuate.spring.cloud.stream.binder.eventuate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EventuateConsumerProperties {

  private Map<String, Set<String>> aggregatesAndEvents = new HashMap<>();

  public Map<String, Set<String>> getAggregatesAndEvents() {
    return aggregatesAndEvents;
  }

  public void setAggregatesAndEvents(Map<String, Set<String>> aggregatesAndEvents) {
    this.aggregatesAndEvents = aggregatesAndEvents;
  }
}
