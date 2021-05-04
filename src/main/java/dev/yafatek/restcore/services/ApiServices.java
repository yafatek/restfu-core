package dev.yafatek.restcore.services;

import dev.yafatek.restcore.api.utils.ApiResponse;
import dev.yafatek.restcore.api.utils.ErrorResponse;
import dev.yafatek.restcore.domain.BaseEntity;
import dev.yafatek.restcore.domain.GenericRepo;
import dev.yafatek.restcore.wrappers.ApiServicesWrapper;

import java.time.Instant;
import java.util.UUID;

/**
 * Entry point to the library
 * it is used to build an object that is used to access the internal methods,
 * we can bas any Class that extends the Base Entity Class
 *
 * @param <T>  the child class that implements the the base entity
 * @param <ID> UUID Based Class to use it as an ID to the System Tables.
 * @see BaseEntity for more information.
 */
public interface ApiServices<T extends BaseEntity, ID extends UUID> {

    default ApiServices<T, ID> build(GenericRepo<T, ID> genericRepo) {
        return new ApiServicesWrapper<>(genericRepo) {
        };
    }

    /**
     * Method to Save the Data to the target Schema Table
     *
     * @param entity the entity to save
     * @return api Response or null in case of failuer.
     */
    ApiResponse<T, ErrorResponse> saveEntity(T entity);

    /**
     * Method to bulk get the data from the database.
     *
     * @return api Response or empty entity in case of Empty Table.
     */
    ApiResponse<T, ErrorResponse> getAll();

    /**
     * Method to Get One row of a targeted table in the Schema.
     *
     * @param entityId the UUID as a row id.
     * @return ApiReponse or null in case of not found element.
     */

    ApiResponse<T, ErrorResponse> getById(ID entityId);

    /**
     * @param after
     * @return
     */
    ApiResponse<T, ErrorResponse> getAllAfter(Instant after);

    ApiResponse<T, ErrorResponse> updateById(T entity, ID entityId);

    ApiResponse<T, ErrorResponse> deleteById(ID entityId);

    ApiResponse<T, ErrorResponse> bulkDelete();


}
