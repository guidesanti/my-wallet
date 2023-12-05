package br.com.eventhorizon.mywallet.common.saga;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
public final class SagaIdempotenceId {

    private static final int DATE_INDEX = 0;

    private static final int DATE_LENGTH = 8;

    private static final String DATE_PATTERN = "(\\d{4})(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[01])";

    private static final int CLIENT_ID_INDEX = DATE_INDEX + DATE_LENGTH;

    private static final int CLIENT_ID_LENGTH = 10;

    private static final String CLIENT_ID_PATTERN = "^[0-9A-Za-z]{10}$";

    private static final int SEQUENCE_NUMBER_INDEX = CLIENT_ID_INDEX + CLIENT_ID_LENGTH;

    private static final int SEQUENCE_NUMBER_LENGTH = 18;

    private static final String SEQUENCE_NUMBER_PATTER = "^[0-9]{18}$";

    private static final int IDEMPOTENCE_STEP_INDEX = SEQUENCE_NUMBER_INDEX + SEQUENCE_NUMBER_LENGTH;

    private static final int IDEMPOTENCE_STEP_LENGTH = 3;

    private static final int IDEMPOTENCE_STEP_MIN = 0;

    private static final int IDEMPOTENCE_STEP_MAX = 999;

    private static final String OPERATION_ID_PATTERN = "(\\d{4})(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[01])([0-9A-Za-z]{10}?)([0-9]{18}?)([0-9]{3}?)";

    String date;

    String clientId;

    String sequenceNumber;

    Integer idempotenceStep;

    public static SagaIdempotenceId of(String date, String clientId, String sequenceNumber) {
        return SagaIdempotenceId.of(date, clientId, sequenceNumber, 0);
    }

    public static SagaIdempotenceId of(String date, String clientId, String sequenceNumber, Integer idempotenceStep) {
        validateDate(date);
        validateClientId(clientId);
        validateSequenceNumber(sequenceNumber);
        validateIdempotenceStep(idempotenceStep);
        return new SagaIdempotenceId(date, clientId, sequenceNumber, idempotenceStep);
    }

    public static SagaIdempotenceId of(String idempotenceId) {
        validateIdempotenceId(idempotenceId);
        return new SagaIdempotenceId(
                idempotenceId.substring(DATE_INDEX, DATE_INDEX + DATE_LENGTH),
                idempotenceId.substring(CLIENT_ID_INDEX, CLIENT_ID_INDEX+ CLIENT_ID_LENGTH),
                idempotenceId.substring(SEQUENCE_NUMBER_INDEX, SEQUENCE_NUMBER_INDEX + SEQUENCE_NUMBER_LENGTH),
                Integer.valueOf(idempotenceId.substring(IDEMPOTENCE_STEP_INDEX, IDEMPOTENCE_STEP_INDEX + IDEMPOTENCE_STEP_LENGTH)));
    }

    @Override
    public String toString() {
        return date + clientId + sequenceNumber + String.format("%03d", idempotenceStep);
    }

    public String date() {
        return date;
    }

    public String clientId() {
        return clientId;
    }

    public String sequenceNumber() {
        return sequenceNumber;
    }

    public Integer idempotenceStep() {
        return idempotenceStep;
    }

    public String operationId() {
        return date + clientId + sequenceNumber;
    }

    public SagaIdempotenceId incrementIdempotenceStep() {
        return SagaIdempotenceId.of(date, clientId, sequenceNumber, idempotenceStep + 1);
    }

    private static void validateDate(String date) {
        Pattern pattern = Pattern.compile(DATE_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(date);
        if (!matcher.find()) {
            throw new InvalidSagaIdempotenceIdException("Invalid date: " + date);
        }
    }

    private static void validateClientId(String clientId) {
        Pattern pattern = Pattern.compile(CLIENT_ID_PATTERN);
        Matcher matcher = pattern.matcher(clientId);
        if (!matcher.find()) {
            throw new InvalidSagaIdempotenceIdException("Invalid client ID: " + clientId);
        }
    }

    private static void validateSequenceNumber(String sequenceNumber) {
        Pattern pattern = Pattern.compile(SEQUENCE_NUMBER_PATTER);
        Matcher matcher = pattern.matcher(sequenceNumber);
        if (!matcher.find()) {
            throw new InvalidSagaIdempotenceIdException("Invalid sequence number: " + sequenceNumber);
        }
    }

    private static void validateIdempotenceStep(Integer idempotenceStep) {
        if (idempotenceStep < IDEMPOTENCE_STEP_MIN || idempotenceStep > IDEMPOTENCE_STEP_MAX) {
            throw new InvalidSagaIdempotenceIdException("Invalid idempotence step: " + idempotenceStep);
        }
    }

    private static void validateIdempotenceId(String operationId) {
        Pattern pattern = Pattern.compile(OPERATION_ID_PATTERN);
        Matcher matcher = pattern.matcher(operationId);
        if (!matcher.find()) {
            throw new InvalidSagaIdempotenceIdException("Invalid idempotence ID: " + operationId);
        }
    }
}
