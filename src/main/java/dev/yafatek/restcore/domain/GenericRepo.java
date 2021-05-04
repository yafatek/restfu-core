package dev.yafatek.restcore.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.time.Instant;

@NoRepositoryBean
public interface GenericRepo<T extends BaseEntity, ID extends Serializable> extends JpaRepository<T, ID> {
    T getByCreatedAfter(Instant after);
}

