package dev.yafatek.restcore.wrappers;

import dev.yafatek.restcore.api.utils.ApiResponse;
import dev.yafatek.restcore.api.utils.ErrorResponse;
import dev.yafatek.restcore.domain.BaseEntity;
import dev.yafatek.restcore.domain.GenericRepo;
import dev.yafatek.restcore.services.ApiServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
public abstract class ApiServicesWrapper<T extends BaseEntity, ID extends UUID> implements ApiServices<T, ID> {

    private final GenericRepo<T, ID> genericRepo;

    public ApiServicesWrapper(GenericRepo<T, ID> genericRepo) {
        this.genericRepo = genericRepo;
    }

    @Override
    public ApiResponse<T, ErrorResponse> saveEntity(T entity) {

        return null;
    }

    @Override
    public ApiResponse<T, ErrorResponse> getAll() {
        return null;
    }

    @Override
    public ApiResponse<T, ErrorResponse> getById(ID entityId) {
        return null;
    }

    @Override
    public ApiResponse<T, ErrorResponse> getAllAfter(Instant after) {
        return null;
    }

    @Override
    public ApiResponse<T, ErrorResponse> updateById(T entity, ID entityId) {
        return null;
    }

    @Override
    public ApiResponse<T, ErrorResponse> deleteById(ID entityId) {
        return null;
    }

    @Override
    public ApiResponse<T, ErrorResponse> bulkDelete() {
        return null;
    }
}
