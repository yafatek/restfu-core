package dev.yafatek.restcore.unittest.v1;

import dev.yafatek.restcore.domain.BaseEntity;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "demo")
public class DemoEntity extends BaseEntity {
    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(name = "id", columnDefinition = "BINARY(36)", updatable = false, nullable = false)
    protected UUID id;

    @org.springframework.data.annotation.Version
    private int version;
    @Lob
    @Column(name = "description")
    protected String description;
    protected String attribute;

    public DemoEntity() {
    }

    public DemoEntity(Instant created, UUID id, int version, String description, String attribute) {
        super(created);
        this.id = id;
        this.version = version;
        this.description = description;
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
