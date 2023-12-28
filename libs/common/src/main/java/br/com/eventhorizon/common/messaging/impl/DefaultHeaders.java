package br.com.eventhorizon.common.messaging.impl;

import br.com.eventhorizon.common.messaging.Headers;
import lombok.NonNull;

import java.util.*;

//public class DefaultHeaders implements Headers {
//
//    private final Map<String, List<String>> headers;
//
//    public static final DefaultHeaders EMPTY_HEADERS = new DefaultHeaders(Collections.emptyMap());
//
//    private DefaultHeaders(Map<String, List<String>> headers) {
//        this.headers = headers == null ? Collections.emptyMap() : headers;
//    }
//
//    @Override
//    public Set<String> names() {
//        return headers.keySet();
//    }
//
//    @Override
//    public List<String> values(String name) {
//        return headers.get(name);
//    }
//
//    public static DefaultHeaders emptyHeaders() {
//        return EMPTY_HEADERS;
//    }
//
//    public static Builder builder() {
//        return new Builder();
//    }
//
//    public static class Builder {
//
//        private final Map<String, List<String>> headers;
//
//        private Builder() {
//            headers = new HashMap<>();
//        }
//
//        public Builder headers(Map<String, List<String>> headers) {
//            if (headers != null) {
//                this.headers.putAll(headers);
//            }
//            return this;
//        }
//
//        public Builder header(@NonNull String name, @NonNull String value) {
//            var values = this.headers.getOrDefault(name, new ArrayList<>());
//            values.add(value);
//            this.headers.put(name, values);
//            return this;
//        }
//
//        public DefaultHeaders build() {
//            return new DefaultHeaders(headers);
//        }
//    }
//}
