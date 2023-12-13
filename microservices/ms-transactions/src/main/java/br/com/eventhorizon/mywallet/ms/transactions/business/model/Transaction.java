package br.com.eventhorizon.mywallet.ms.transactions.business.model;

import br.com.eventhorizon.common.common.Error;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Getter
@Builder
public class Transaction {

    private String id;

    @NonNull
    @Builder.Default
    private OffsetDateTime createdAt = OffsetDateTime.now(ZoneOffset.UTC);

    private OffsetDateTime settledAt;

    @NonNull
    @Builder.Default
    private State state = State.PENDING;

    @NonNull
    private Type type;

    @Builder.Default
    private BigDecimal units = BigDecimal.ONE;

    @NonNull
    private BigDecimal price;

    private String sourceAccountId;

    private String destinationAccountId;

    private String assetId;

    private String description;

    @NonNull
    @Singular
    private List<Error> errors;

    public BigDecimal getAmount() {
        return price.multiply(units);
    }

    public void settle() {
        this.settledAt = OffsetDateTime.now(ZoneOffset.UTC);
        this.state = State.SETTLED;
    }

    public void reject(List<Error> errors) {
        this.errors.addAll(errors);
        this.state = State.REJECTED;
    }

    public void fail(List<Error> errors) {
        this.errors.addAll(errors);
        this.state = State.FAILED;
    }

    public enum Type {
        ASSET_BUY,
        ASSET_SELL,
        TRANSFER,
        DEPOSIT,
        WITHDRAW,
        DIVIDEND,
        INTEREST,
        CASHBACK;
    }

    public enum State {
        PENDING,
        SETTLED,
        REJECTED,
        FAILED;
    }
}
