package dev.yafatek.restcore.domain;

//import dev.yafatek.restcore.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "demo")
public class DemoEntity extends BaseEntity {

    @Lob
    @Column(name = "description")
    protected String description;
    protected String attribute;

    public DemoEntity() {
    }

    public DemoEntity(UUID id, Instant created, String description, String attribute) {
        super(id, created);
        this.description = description;
        this.attribute = attribute;
    }

    public DemoEntity(String description, String attribute) {
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


}
