package dev.yafatek.restcore.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@NoRepositoryBean
@Transactional
public interface GenericRepo<T extends BaseEntity, ID extends Serializable> extends JpaRepository<T, ID> {
    List<T> findAllByCreatedAfter(Instant after);
//    T findByVersion(int version);
    //  List<CallLog> findAllByCallTimeAfter(Instant after);
}

