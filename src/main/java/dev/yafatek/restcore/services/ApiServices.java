package dev.yafatek.restcore.services;

import dev.yafatek.restcore.api.utils.ApiResponse;
import dev.yafatek.restcore.api.utils.ErrorResponse;
import dev.yafatek.restcore.domain.BaseEntity;

import java.time.Instant;
import java.util.UUID;

public interface ApiServices<T extends BaseEntity, ID extends UUID> {

    ApiResponse<T, ErrorResponse> saveEntity(T entity);

    ApiResponse<T, ErrorResponse> getAll();

    ApiResponse<T, ErrorResponse> getById(ID entityId);

    ApiResponse<T, ErrorResponse> getAllAfter(Instant after);

    ApiResponse<T, ErrorResponse> updateById(T entity, ID entityId);

    ApiResponse<T, ErrorResponse> deleteById(ID entityId);

    ApiResponse<T, ErrorResponse> bulkDelete();


}
