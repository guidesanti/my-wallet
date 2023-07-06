package br.com.eventhorizon.myinvestments.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public abstract class BaseModel {

    @Id
    protected String id;

    protected Long createdAt;

    protected Long updatedAt;

    @Override
    public String toString() {
        return "BaseModel{" +
                "id='" + id + '\'' +
                ", createdAt='" + LocalDateTime.ofInstant(Instant.ofEpochMilli(createdAt), ZoneId.of("UTC")) + '\'' +
                ", updatedAt='" + LocalDateTime.ofInstant(Instant.ofEpochMilli(updatedAt), ZoneId.of("UTC")) + '\'' +
                '}';
    }
}
