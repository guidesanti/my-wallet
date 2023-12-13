package br.com.eventhorizon.common.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseModel {

    @Id
    protected String id;

    @Version
    protected Integer version;

    protected String createdAt;

    protected String updatedAt;
}
