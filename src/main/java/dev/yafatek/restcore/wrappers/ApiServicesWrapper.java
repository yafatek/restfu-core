package dev.yafatek.restcore.wrappers;

import dev.yafatek.restcore.api.responses.ApiResponse;
import dev.yafatek.restcore.api.enums.ApiResponseCodes;
import dev.yafatek.restcore.api.utils.ApiUtils;
import dev.yafatek.restcore.api.responses.ErrorResponse;
import dev.yafatek.restcore.api.responses.DeleteResponse;
import dev.yafatek.restcore.domain.BaseEntity;
import dev.yafatek.restcore.domain.GenericRepo;
import dev.yafatek.restcore.services.ApiServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static dev.yafatek.restcore.api.utils.ApiUtils.*;

/**
 * Wrapper class for the ApiService
 *
 * @param <T>  actual Type
 * @param <ID> the Is is UUID
 * @author Feras E.Alawadi
 * @version 1.0.101
 * @see UUID
 * @since 1.0.101
 */
@Service
@Transactional
public abstract class ApiServicesWrapper<T extends BaseEntity, ID extends Serializable> implements ApiServices<T, ID> {

    /**
     * Cant Find String Response
     */
    protected static final String CANT_FIND = "Can't Load The Data";

    /**
     * Can't Update String Response
     */
    protected static final String CANT_UPDATE = "can't update resource";
    /**
     * Can't Create event String Response
     */
    protected static final String CANT_CREATE = "can't create resource";
    /**
     * Data Loaded String Response
     */
    protected static final String DATA_LOADED = "Data Loaded";
    /**
     * Data Saved String Response
     */
    protected static final String DATA_SAVED = "new resource saved!";
    /**
     * Calss Events Logger
     */
    protected static final Logger LOGGER = LoggerFactory.getLogger(ApiServicesWrapper.class);
    /**
     * Generic Repository
     */
    protected final GenericRepo<T, ID> genericRepo;

    /**
     * construct new object
     *
     * @param genericRepo the repository to be used with in the  class
     */
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
    public ApiResponse<List<T>, ErrorResponse> getAll() {
        LOGGER.info(" [.] get All Table Data...");
        List<T> results = genericRepo.findAll();
        return results.isEmpty() ?
                errorResponse(false, CANT_FIND, ApiResponseCodes.TABLE_EMPTY.toString(),
                        new ErrorResponse("target table is empty", ApiResponseCodes.TABLE_EMPTY.toString()))
                : success(true, "all data loaded!", ApiResponseCodes.SUCCESS.toString(), results);
    }

    @Override
    public ApiResponse<T, ErrorResponse> getById(ID entityId) {
        if (checkId(entityId))
            return errorResponse(false, CANT_UPDATE, ApiResponseCodes.FAIL.toString(),
                    new ErrorResponse("the is is not a valid uuid", ApiResponseCodes.FAIL.toString()));
        return genericRepo.findById(entityId)
                .map(t -> success(true, "resource data loaded", ApiResponseCodes.SUCCESS.name(), t))
                .orElseGet(() -> errorResponse(false, CANT_FIND, ApiResponseCodes.RESOURCE_NOT_FOUND.name(),
                        new ErrorResponse("No Data Found For the Requested Resource", ApiResponseCodes.RESOURCE_NOT_FOUND.name())));
    }

    @Override
    public ApiResponse<List<T>, ErrorResponse> getAllAfter(Instant after) {
        List<T> exist = genericRepo.findAllByCreatedAfter(after);
        return exist.isEmpty()
                ? errorResponse(false, CANT_FIND, ApiResponseCodes.RESOURCE_NOT_FOUND.toString(),
                new ErrorResponse("can't find data after that date", ApiResponseCodes.RESOURCE_NOT_FOUND.toString()))
                : success(true, DATA_LOADED, ApiResponseCodes.SUCCESS.toString(), exist);
    }

    @Override
    public ApiResponse<T, ErrorResponse> updateById(T entity, ID entityId) {
        if (checkId(entityId))
            return errorResponse(false, CANT_UPDATE, ApiResponseCodes.FAIL.toString(),
                    new ErrorResponse("the is is not a valid uuid", ApiResponseCodes.FAIL.toString()));

        Optional<T> optional = genericRepo.findById(entityId);
        if (optional.isPresent()) {
            entity.setId((UUID) entityId);
            entity = genericRepo.save(entity);
            return successResponse(true, "update success", ApiResponseCodes.SUCCESS.toString(), entity, null);
        } else {
            return errorResponse(false, CANT_UPDATE, ApiResponseCodes.RESOURCE_NOT_FOUND.toString(),
                    new ErrorResponse("Can't find resource with id: { " + entityId + " }", ApiResponseCodes.RESOURCE_NOT_FOUND.toString()));
        }
    }

    @Override
    public ApiResponse<DeleteResponse, ErrorResponse> deleteById(ID entityId) {
        if (checkId(entityId))
            return errorResponse(false, CANT_UPDATE, ApiResponseCodes.FAIL.toString(),
                    new ErrorResponse("the is is not a valid uuid", ApiResponseCodes.FAIL.toString()));
        Optional<T> exist = genericRepo.findById(entityId);
        if (exist.isEmpty())
            return errorResponse(false, "Can't Delete", ApiResponseCodes.RESOURCE_NOT_FOUND.name(),
                    new ErrorResponse("Nothing to delete, resource: { " + entityId + " } not exist", ApiResponseCodes.RESOURCE_NOT_FOUND.name()));

        genericRepo.deleteById(entityId);
        return success(true, "delete Done", ApiResponseCodes.SUCCESS.toString(), new DeleteResponse("1"));
    }

    @Override
    public ApiResponse<DeleteResponse, ErrorResponse> bulkDelete() {
        List<T> all = genericRepo.findAll();
        if (all.isEmpty())
            return errorResponse(false, "Can't Delete", ApiResponseCodes.TABLE_EMPTY.name(),
                    new ErrorResponse("Nothing to delete, table is empty", ApiResponseCodes.TABLE_EMPTY.name()));

        genericRepo.deleteAll();
        return success(true, "delete Done", ApiResponseCodes.SUCCESS.toString(), new DeleteResponse("1"));
    }


    @Override
    public boolean checkId(ID id) {
        return !ApiUtils.validUUID(id.toString());
    }
}
