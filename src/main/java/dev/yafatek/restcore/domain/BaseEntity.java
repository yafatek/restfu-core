package dev.yafatek.restcore.domain;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * Generic Entity used with the Api Implementation
 *
 * @author Feras E. Alawadi
 * @version 1.0.101
 * @since 1.0.101
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseEntity implements Serializable {
    /**
     * the table id
     */
    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    @Column(name = "id", columnDefinition = "BINARY(36)", updatable = false, nullable = false)
    protected UUID id;

    /**
     * our API Version
     */
    @Version
    protected int version = 1;

    /**
     * created resource Date
     */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    @JsonDeserialize(using = InstantDeserializer.class)
    @JsonSerialize(using = InstantSerializer.class)
    private Instant created;

    /**
     * modifiying the  resource Date
     */
    @LastModifiedDate
    @JsonDeserialize(using = InstantDeserializer.class)
    @JsonSerialize(using = InstantSerializer.class)
    private Instant modified;

    /**
     * default constructor
     */
    public BaseEntity() {
    }

    /**
     * create new objects with values
     *
     * @param id      the id
     * @param created Instant object
     */
    public BaseEntity(UUID id, Instant created) {
        this.id = id;
        this.created = created;
    }

    /**
     * to get the API Version
     *
     * @return the version
     */
    public int getVersion() {
        return version;
    }

    /**
     * setting the API Version
     *
     * @param version version
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * get the resouce ID
     *
     * @return UUID
     */
    public UUID getId() {
        return id;
    }

    /**
     * set the resource id
     *
     * @param id UUID
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * create resource date
     *
     * @return create date
     */
    public Instant getCreated() {
        return created;
    }

    /**
     * set the create date
     *
     * @param created Instant Object
     */
    public void setCreated(Instant created) {
        this.created = created;
    }

    /**
     * get the modification date
     *
     * @return Instant
     */
    public Instant getModified() {
        return modified;
    }

    /**
     * change the modification date
     *
     * @param modified Instant
     */

    public void setModified(Instant modified) {
        this.modified = modified;
    }
}
