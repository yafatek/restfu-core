package dev.yafatek.restcore.services;

import dev.yafatek.restcore.api.responses.ApiResponse;
import dev.yafatek.restcore.api.responses.DeleteResponse;
import dev.yafatek.restcore.api.responses.ErrorResponse;
import dev.yafatek.restcore.domain.BaseEntity;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

/**
 * Entry point to the library
 * it is used to build an object that is used to access the internal methods,
 * we can bas any Class that extends the Base Entity Class
 *
 * @param <T>  the child class that implements the the base entity
 * @param <ID> UUID Based Class to use it as an ID to the System Tables.
 *             // * @see BaseEntity for more information.
 */
public interface ApiServices<T extends BaseEntity, ID extends Serializable> {

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
    ApiResponse<List<T>, ErrorResponse> getAll();

    /**
     * Method to Get One row of a targeted table in the Schema.
     *
     * @param entityId the UUID as a row id.
     * @return ApiReponse or null in case of not found element.
     */

    ApiResponse<T, ErrorResponse> getById(ID entityId);

    /**
     * method to get all resources after a specific date.
     *
     * @param after the Instant Object
     * @return response
     */
    ApiResponse<List<T>, ErrorResponse> getAllAfter(Instant after);

    /**
     * method to update a resource based on its id
     *
     * @param entity   the resource entity
     * @param entityId the resource id
     * @return api response with the updated data
     */
    ApiResponse<T, ErrorResponse> updateById(T entity, ID entityId);

    /**
     * method to delete resource in the Database
     *
     * @param entityId the resource id
     * @return ApiResponse with the operation results.
     */
    ApiResponse<DeleteResponse, ErrorResponse> deleteById(ID entityId);

    /**
     * method to bulk delete the data in a Table.
     *
     * @return api respons with the result
     */
    ApiResponse<DeleteResponse, ErrorResponse> bulkDelete();

    /**
     * method to check if the UUID is Valid id
     *
     * @param id the uuid
     * @return boolean
     */
    boolean checkId(ID id);

}
