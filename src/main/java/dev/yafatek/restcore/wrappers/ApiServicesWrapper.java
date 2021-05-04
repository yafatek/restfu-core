package dev.yafatek.restcore.wrappers;

import dev.yafatek.restcore.api.utils.ApiResponse;
import dev.yafatek.restcore.api.utils.ApiUtils;
import dev.yafatek.restcore.api.utils.ErrorResponse;
import dev.yafatek.restcore.domain.BaseEntity;
import dev.yafatek.restcore.domain.GenericRepo;
import dev.yafatek.restcore.services.ApiServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
public abstract class ApiServicesWrapper<T extends BaseEntity, ID extends UUID> implements ApiServices<T, ID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiServicesWrapper.class);
    //todo static getInstance Method.
    private final GenericRepo<T, ID> genericRepo;

    public ApiServicesWrapper(GenericRepo<T, ID> genericRepo) {
        LOGGER.info(" [.] init JPA Wrapper...");
        this.genericRepo = genericRepo;
    }

    @Override
    public ApiResponse<T, ErrorResponse> saveEntity(T entity) {
        LOGGER.info(" [.] saving new entity");
        return new ApiResponse<>(false, "", "", genericRepo.save(entity));
    }


    @Override
    public ApiResponse<T, ErrorResponse> getAll() {
        LOGGER.info(" [.] get All Table Data...");
        return ApiUtils.successResponse(true, "", "", null, new ErrorResponse());
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
        if (!ApiUtils.validUUID(String.valueOf(entityId)))
            return null;

        return genericRepo.findById(entityId).map(exist -> {
            exist.setModified(Instant.now());
            exist = genericRepo.save(exist);
//             entity.getClass().get
            return ApiUtils.successResponse(true, "", "", exist, new ErrorResponse());
        }).orElse(ApiUtils.errorResponse(false, "", "", new ErrorResponse("", "")));
//        return isExist == null ? null : new ApiResponse<>(true,":", "",isExist);
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
