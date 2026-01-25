package com.yongman.common.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ApiResponse<T> {

    private final boolean success;
    private final T data;
    private final LocalDateTime timestamp;

    private ApiResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, data);
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(true, null);
    }

    public static <T> ApiResponse<T> fail(T data) {
        return new ApiResponse<>(false, data);
    }
}
