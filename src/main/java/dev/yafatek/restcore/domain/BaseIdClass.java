package dev.yafatek.restcore.domain;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

public class BaseIdClass implements Serializable {
    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(name = "id", columnDefinition = "BINARY(36)", updatable = false, nullable = false)
    protected UUID id;

    public BaseIdClass() {
    }

    public BaseIdClass(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}