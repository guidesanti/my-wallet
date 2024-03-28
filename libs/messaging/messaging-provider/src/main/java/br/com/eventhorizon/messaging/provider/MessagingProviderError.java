package br.com.eventhorizon.messaging.provider;

import br.com.eventhorizon.common.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessagingProviderError {

    UNEXPECTED_ERROR("Unexpected error: %s"),
    MESSAGE_PUBLISH_TO_SOURCE_FAILED("Failed to publish message back to source '%s' with retry count '%s'"),
    MESSAGE_PUBLISH_TO_DESTINATION_FAILED("Failed to publish message destination '%s'");

    public static final String DOMAIN = "MESSAGING_PROVIDER";

    private final String messageTemplate;

    public ErrorCode getCode() {
        return ErrorCode.lib(DOMAIN, this.name());
    }

    public String getMessage(Object ...args) {
        return String.format(messageTemplate, args);
    }
}
