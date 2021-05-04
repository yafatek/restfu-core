package dev.yafatek.restcore.wrappers;

//import dev.yafatek.restcore.domain.BaseEntity;
import dev.yafatek.restcore.domain.BaseEntity;
import dev.yafatek.restcore.domain.GenericRepo;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApiServiceImpl<T extends BaseEntity, ID extends UUID> extends ApiServicesWrapper<T, ID> {
    public ApiServiceImpl(GenericRepo<T, ID> genericRepo) {
        super(genericRepo);
    }
}
