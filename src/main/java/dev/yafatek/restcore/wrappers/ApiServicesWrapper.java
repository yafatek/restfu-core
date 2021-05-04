package dev.yafatek.restcore.wrappers;

import dev.yafatek.restcore.api.utils.*;
import dev.yafatek.restcore.domain.BaseEntity;
import dev.yafatek.restcore.domain.GenericRepo;
import dev.yafatek.restcore.services.ApiServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static dev.yafatek.restcore.api.utils.ApiUtils.*;

@Service
@Transactional
public abstract class ApiServicesWrapper<T extends BaseEntity, ID extends Serializable> implements ApiServices<T, ID> {

    protected static final String CANT_FIND = "Can't Load The Data";
    protected static final String CANT_UPDATE = "can't update resource";
    protected static final String CANT_CREATE = "can't create resource";
    protected static final String DATA_LOADED = "Data Loaded";
    protected static final String DATA_SAVED = "new resource saved!";
    protected static final Logger LOGGER = LoggerFactory.getLogger(ApiServicesWrapper.class);
    protected final GenericRepo<T, ID> genericRepo;

    @Autowired
    public ApiServicesWrapper(GenericRepo<T, ID> genericRepo) {
        this.genericRepo = genericRepo;
        LOGGER.info(" [*] generic repository: {}", genericRepo);
    }

    @Override
    public ApiResponse<T, ErrorResponse> saveEntity(T entity) {
        LOGGER.info(" [.] saving new entity");
        try {
            T saved = genericRepo.save(entity);
            return success(true, DATA_SAVED, ApiResponseCodes.SUCCESS.name(), saved);
        } catch (Exception ignore) {
            return errorResponse(false, CANT_CREATE, ApiResponseCodes.FAIL.name(),
                    new ErrorResponse("there is a problem creating new resource", ApiResponseCodes.FAIL.name()));
        }
    }


    @Override
    public ApiResponse<T, ErrorResponse> getAll() {
        LOGGER.info(" [.] get All Table Data...");
        List<T> results = genericRepo.findAll();
        return results.isEmpty() ?
                errorResponse(false, CANT_FIND, ApiResponseCodes.TABLE_EMPTY.toString(),
                        new ErrorResponse("target table is empty", ApiResponseCodes.TABLE_EMPTY.toString()))
                : successResponse(true, "all data loaded!", ApiResponseCodes.SUCCESS.toString(), (T) results, null);
    }

    @Override
    public ApiResponse<T, ErrorResponse> getById(ID entityId) {
        if (!checkId(entityId))
            return errorResponse(false, CANT_UPDATE, ApiResponseCodes.FAIL.toString(),
                    new ErrorResponse("the is is not a valid uuid", ApiResponseCodes.FAIL.toString()));
        return genericRepo.findById(entityId)
                .map(t -> success(true, "resource data loaded", ApiResponseCodes.SUCCESS.name(), t))
                .orElseGet(() -> errorResponse(false, CANT_FIND, ApiResponseCodes.RESOURCE_NOT_FOUND.name(),
                        new ErrorResponse("No Data Found For the Requested Resource", ApiResponseCodes.RESOURCE_NOT_FOUND.name())));
    }

    @Override
    public ApiResponse<T, ErrorResponse> getAllAfter(Instant after) {
        return null;
//        T exist = genericRepo.findByCreatedAfter(after);
//        return exist == null
//                ? errorResponse(false, CANT_FIND, ApiResponseCodes.RESOURCE_NOT_FOUND.toString(),
//                new ErrorResponse("can't find data after that date", ApiResponseCodes.RESOURCE_NOT_FOUND.toString()))
//                : success(true, DATA_LOADED, ApiResponseCodes.SUCCESS.toString(), exist);
    }

    @Override
    public ApiResponse<T, ErrorResponse> updateById(T entity, ID entityId) {
        if (!checkId(entityId))
            return errorResponse(false, CANT_UPDATE, ApiResponseCodes.FAIL.toString(),
                    new ErrorResponse("the is is not a valid uuid", ApiResponseCodes.FAIL.toString()));

        Optional<T> optional = genericRepo.findById(entityId);
        if (optional.isPresent()) {
            entity = genericRepo.save(entity);
            return successResponse(true, "update success", ApiResponseCodes.SUCCESS.toString(), entity, null);
        } else {
            return errorResponse(false, CANT_UPDATE, ApiResponseCodes.RESOURCE_NOT_FOUND.toString(),
                    new ErrorResponse("Can't find resource with id: { " + entityId + " }", ApiResponseCodes.RESOURCE_NOT_FOUND.toString()));
        }
    }

    @Override
    public ApiResponse<DeleteResponse, ErrorResponse> deleteById(ID entityId) {
        if (!checkId(entityId))
            return errorResponse(false, CANT_UPDATE, ApiResponseCodes.FAIL.toString(),
                    new ErrorResponse("the is is not a valid uuid", ApiResponseCodes.FAIL.toString()));
        genericRepo.deleteById(entityId);
        return success(true, "delete Done", ApiResponseCodes.SUCCESS.toString(), new DeleteResponse("1"));
    }

    @Override
    public ApiResponse<DeleteResponse, ErrorResponse> bulkDelete() {
        genericRepo.deleteAll();
        return success(true, "delete Done", ApiResponseCodes.SUCCESS.toString(), new DeleteResponse("1"));
    }


    @Override
    public boolean checkId(ID id) {
        return ApiUtils.validUUID(id.toString());
    }
}
