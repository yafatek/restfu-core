package dev.yafatek.restcore.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

/**
 * https://stackoverflow.com/questions/19417670/using-generics-in-spring-data-jpa-repositories
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity implements Serializable {


    @CreatedDate
    @Column(nullable = false, updatable = false)
    @JsonDeserialize(using = InstantDeserializer.class)
    @JsonSerialize(using = InstantSerializer.class)
    private Instant created;

    @LastModifiedDate
    @JsonDeserialize(using = InstantDeserializer.class)
    @JsonSerialize(using = InstantSerializer.class)
    private Instant modified;

    public BaseEntity() {
    }

    public BaseEntity(Instant created) {
        this.created = created;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getModified() {
        return modified;
    }

    public void setModified(Instant modified) {
        this.modified = modified;
    }
}
