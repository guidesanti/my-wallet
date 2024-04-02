package br.com.eventhorizon.messaging.provider.subscription;

import br.com.eventhorizon.common.properties.Properties;
import br.com.eventhorizon.common.properties.PropertyDefinition;
import br.com.eventhorizon.common.properties.PropertyDefinitions;

import java.util.Collections;
import java.util.Map;

public class SubscriptionProperties extends Properties {

    public static final String CONCURRENCY = "concurrency";

    public static final String POLL_INTERVAL_MS = "poll.interval.ms";

    private static final PropertyDefinitions PROPERTY_DEFINITIONS = new PropertyDefinitions()
            .add(PropertyDefinition.of(CONCURRENCY, Integer.class, 1, "Number of poll threads to be used."))
            .add(PropertyDefinition.of(POLL_INTERVAL_MS, Integer.class, 100, "Poll interval in ms."));

    public SubscriptionProperties() {
        super(PROPERTY_DEFINITIONS, Collections.emptyMap());
    }

    public SubscriptionProperties(Map<String, Object> properties) {
        super(PROPERTY_DEFINITIONS, properties);
    }
}
