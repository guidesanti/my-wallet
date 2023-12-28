package br.com.eventhorizon.common.messaging;

import lombok.*;

import java.util.*;

@EqualsAndHashCode
public class Headers implements Iterable<Map.Entry<String, List<String>>> {

    public static final Headers EMPTY_HEADERS = new Headers();

    private final Map<String, List<String>> headers;

    private Headers() {
        this.headers = new HashMap<>();
    }

    private Headers(Builder builder) {
        this.headers = builder.headers;
    }

    public static Headers emptyHeaders() {
        return EMPTY_HEADERS;
    }

    public Set<String> names() {
        return headers.keySet();
    }

    public List<String> values(String name) {
        return headers.getOrDefault(name, Collections.emptyList());
    }

    public Optional<String> firstValue(String name) {
        return values(name).stream().findFirst();
    }

    public boolean isEmpty() {
        return headers.isEmpty();
    }

    public boolean contains(String name) {
        return headers.containsKey(name);
    }

    @Override
    public Iterator<Map.Entry<String, List<String>>> iterator() {
        Iterator<String> headerNames = names().iterator();

        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return headerNames.hasNext();
            }

            @Override
            public Map.Entry<String, List<String>> next() {
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
                    public List<String> getValue() {
                        return values(name);
                    }

                    @Override
                    public List<String> setValue(List<String> value) {
                        throw new UnsupportedOperationException("Immutable headers cannot be changed");
                    }
                };
            }
        };
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Map<String, List<String>> headers = new HashMap<>();

        public Builder header(@NonNull String name, @NonNull String value) {
            var currentValues = this.headers.getOrDefault(name, new ArrayList<>());
            currentValues.add(value);
            this.headers.put(name, currentValues);
            return this;
        }

        public Builder header(@NonNull String name, @NonNull List<String> values) {
            var currentValues = this.headers.getOrDefault(name, new ArrayList<>());
            currentValues.addAll(values);
            this.headers.put(name, currentValues);
            return this;
        }

        public Builder headers(@NonNull Map<String, List<String>> headers) {
            headers.keySet().forEach(name -> {
                var currentValues = this.headers.getOrDefault(name, new ArrayList<>());
                currentValues.addAll(headers.get(name));
                this.headers.put(name, currentValues);
            });
            return this;
        }

        public Builder headers(@NonNull Headers headers) {
            headers.forEach(entry -> {
                var currentValues = this.headers.getOrDefault(entry.getKey(), new ArrayList<>());
                currentValues.addAll(entry.getValue());
                this.headers.put(entry.getKey(), currentValues);
            });
            return this;
        }

        public Headers build() {
            return new Headers(this);
        }
    }
}
