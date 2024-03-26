package br.com.eventhorizon.messaging.provider.subscription;

import br.com.eventhorizon.common.properties.Properties;
import br.com.eventhorizon.common.properties.PropertyDefinition;
import br.com.eventhorizon.common.properties.PropertyDefinitions;

import java.util.Collections;
import java.util.Map;

public class SubscriptionProperties extends Properties {

    public static final String POLL_CONCURRENCY = "poll.concurrency";

    public static final String POLL_INTERVAL_MS = "poll.interval.ms";

    public static final String PROCESS_CONCURRENCY = "process.concurrency";

    private static final PropertyDefinitions PROPERTY_DEFINITIONS = new PropertyDefinitions()
            .add(PropertyDefinition.of(POLL_CONCURRENCY, Integer.class, 1, "Number of poll threads to be used."))
            .add(PropertyDefinition.of(POLL_INTERVAL_MS, Integer.class, 100, "Poll interval in ms."))
            .add(PropertyDefinition.of(PROCESS_CONCURRENCY, Integer.class, 0, "Number of process threads to be used. If this is 0 process will occur on the poll thread (default behavior)."));

    public SubscriptionProperties() {
        super(PROPERTY_DEFINITIONS, Collections.emptyMap());
    }

    public SubscriptionProperties(Map<String, Object> properties) {
        super(PROPERTY_DEFINITIONS, properties);
    }
}
