package dev.yafatek.restcore.unittest.v1;

import dev.yafatek.restcore.domain.BaseEntity;
import dev.yafatek.restcore.domain.GenericRepo;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DemoRepo<T extends BaseEntity, ID extends UUID> extends GenericRepo<T, ID> {
}
