package io.eventuate.spring.cloud.stream.binder.eventuate;

import io.eventuate.EntityIdAndVersion;
import io.eventuate.Event;
import io.eventuate.EventuateAggregateStore;
import io.eventuate.example.banking.domain.Account;
import io.eventuate.example.banking.domain.CreateAccountCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = EventuateBinderIntegrationTestConfiguration.class)
@IntegrationTest
public class EventuateBinderIntegrationTest {

  @Autowired
  private EventuateAggregateStore aggregateStore;

  @Autowired
  private ExampleMessageConsumer exampleMessageConsumer;

  @Test
  public void shouldConsumeEvent() throws ExecutionException, InterruptedException {
    Account account = new Account();
    List<Event> events = account.process(new CreateAccountCommand(new BigDecimal(123)));

    EntityIdAndVersion r = aggregateStore.save(Account.class, events).get();


    exampleMessageConsumer.getEvents().eventuallyContains(ctx ->
            r.getEntityVersion().asString().equals(ctx.getHeaders().get("eventId"))
    );

  }
}
