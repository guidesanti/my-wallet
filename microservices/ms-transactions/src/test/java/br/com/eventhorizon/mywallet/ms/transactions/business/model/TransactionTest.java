package br.com.eventhorizon.mywallet.ms.transactions.business.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    private static final String ID = "id";

    private static final String SOURCE_ACCOUNT_ID = "source-account-id";

    private static final String DESTINATION_ACCOUNT_ID = "destination-account-id";

    private static Validator validator;

    @BeforeAll
    public static void beforeAll() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void testConstructor() {
        // This should not compile, constructor must be private
//        var transaction = new Transaction("id", OffsetDateTime.now(), OffsetDateTime.now(),Transaction.State.PENDING,
//                Transaction.Type.ASSET_BUY, 1, BigDecimal.valueOf(10), "source-account-id",
//                "destination-account-id", null, Collections.emptyList());
    }

    @Test
    public void testDefaultValues() {
        var transaction = Transaction.builder()
                .id(ID)
                .type(Transaction.Type.TRANSFER)
                .price(BigDecimal.valueOf(1))
                .sourceAccountId(SOURCE_ACCOUNT_ID)
                .destinationAccountId(DESTINATION_ACCOUNT_ID)
                .build();

        assertNotNull(transaction.getCreatedAt());
        assertTrue(transaction.getCreatedAt().isBefore(OffsetDateTime.now()));
        assertEquals(Transaction.State.PENDING, transaction.getState());
        assertEquals(BigDecimal.ONE, transaction.getUnits());
        assertNotNull(transaction.getErrors());
        assertTrue(transaction.getErrors().isEmpty());
    }

    @Test
    public void testPriceIsLessThanOne() {
        var transaction = Transaction.builder()
                .id(ID)
                .type(Transaction.Type.TRANSFER)
                .units(BigDecimal.ZERO)
                .price(BigDecimal.valueOf(1))
                .sourceAccountId(SOURCE_ACCOUNT_ID)
                .destinationAccountId(DESTINATION_ACCOUNT_ID)
                .build();

        Set<ConstraintViolation<Transaction>> constraintViolations = validator.validate(transaction);
        var iterator = constraintViolations.iterator();

        assertEquals(1, constraintViolations.size());
        assertEquals("Units must be grater than 0", iterator.next().getMessage());

        transaction = Transaction.builder()
                .id(ID)
                .type(Transaction.Type.TRANSFER)
                .units(BigDecimal.valueOf(-1))
                .price(BigDecimal.valueOf(1))
                .sourceAccountId(SOURCE_ACCOUNT_ID)
                .destinationAccountId(DESTINATION_ACCOUNT_ID)
                .build();

        constraintViolations = validator.validate(transaction);
        iterator = constraintViolations.iterator();

        assertEquals(1, constraintViolations.size());
        assertEquals("Units must be grater than 0", iterator.next().getMessage());
    }
}
