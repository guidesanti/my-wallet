package br.com.eventhorizon.mywallet.common.message;

import java.util.*;

public interface Headers extends Iterable<Map.Entry<String, List<String>>> {

    Set<String> names();

    List<String> values(String name);

    default Optional<String> firstValue(String name) {
        return values(name).stream().findFirst();
    }

    default boolean isEmpty() {
        return names().isEmpty();
    }

    default boolean contains(String name) {
        return names().contains(name);
    }

    @Override
    default Iterator<Map.Entry<String, List<String>>> iterator() {
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
}
