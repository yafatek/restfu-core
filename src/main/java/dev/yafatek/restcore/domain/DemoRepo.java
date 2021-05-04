package dev.yafatek.restcore.domain;

import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DemoRepo<T extends BaseEntity, ID extends UUID> extends GenericRepo<T, ID> {
}
