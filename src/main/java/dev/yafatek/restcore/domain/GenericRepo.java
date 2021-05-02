package dev.yafatek.restcore.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@NoRepositoryBean
public interface GenericRepo<T extends BaseEntity, ID extends UUID> extends JpaRepository<T,ID> {
}
