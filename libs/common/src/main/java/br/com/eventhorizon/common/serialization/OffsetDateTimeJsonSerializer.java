package br.com.eventhorizon.common.serialization;

import br.com.eventhorizon.common.Common;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.OffsetDateTime;

public class OffsetDateTimeJsonSerializer extends JsonSerializer<OffsetDateTime> {

    @Override
    public void serialize(OffsetDateTime value,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {
        if (value == null) {
            return;
        }
        jsonGenerator.writeString(Common.DEFAULT_DATE_TIME_FORMATTER.format(value));
    }
}
