package dev.yafatek.restcore.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * Generic Spring JPA Database Repository
 *
 * @param <T>  the entity
 * @param <ID> UUId
 * @author Feras E. Alawadi
 * @version 1.0.101
 * @since 1.0.101
 */
@NoRepositoryBean
@Transactional
public interface GenericRepo<T extends BaseEntity, ID extends Serializable> extends JpaRepository<T, ID> {
    /**
     * Method to list all the rows that contains data after specific date
     *
     * @param after the date instance
     * @return list of all rows
     */
    List<T> findAllByCreatedAfter(Instant after);
}

