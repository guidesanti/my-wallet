package br.com.eventhorizon.common.messaging;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.*;

@ToString
@EqualsAndHashCode
public class Attributes implements Iterable<Map.Entry<String, String>> {

    public static final Attributes EMPTY_ATTRIBUTES = new Attributes();

    protected final Map<String, String> attributes;

    private Attributes() {
        this.attributes = new HashMap<>();
    }

    private Attributes(Builder builder) {
        this.attributes = new HashMap<>(builder.attributes);
    }

    public static Attributes emptyAttributes() {
        return EMPTY_ATTRIBUTES;
    }

    public Set<String> names() {
        return attributes.keySet();
    }

    public Collection<String> values() {
        return attributes.values();
    }

    public String value(String name) {
        return attributes.getOrDefault(name, null);
    }

    public boolean isEmpty() {
        return attributes.isEmpty();
    }

    public boolean contains(String name) {
        return attributes.containsKey(name);
    }

    @Override
    public Iterator<Map.Entry<String, String>> iterator() {

        Iterator<String> headerNames = names().iterator();

        return new Iterator<>() {

            @Override
            public boolean hasNext() {
                return headerNames.hasNext();
            }

            @Override
            public Map.Entry<String, String> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                String name = headerNames.next();

                return new Map.Entry<>() {
                    @Override
                    public String getKey() {
                        return name;
                    }

                    @Override
                    public String getValue() {
                        return value(name);
                    }

                    @Override
                    public String setValue(String value) {
                        throw new UnsupportedOperationException("Immutable attributes cannot be changed");
                    }
                };
            }
        };
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        protected final Map<String, String> attributes = new HashMap<>();

        public Builder attributes(@NonNull Attributes attributes) {
            this.attributes.putAll(attributes.attributes);
            return this;
        }

        public Builder attributes(@NonNull Map<String, String> headers) {
            this.attributes.putAll(headers);
            return this;
        }

        public Builder attribute(@NonNull String name, @NonNull String value) {
            this.attributes.put(name, value);
            return this;
        }

        public Attributes build() {
            return new Attributes(this);
        }
    }
}
