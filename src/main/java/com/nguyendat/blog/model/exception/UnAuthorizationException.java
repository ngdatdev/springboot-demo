package com.nguyendat.blog.model.exception;

import com.nguyendat.blog.payload.response.ApiResponse;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
@Data
public class UnAuthorizationException extends RuntimeException {
    private ApiResponse apiResponse;

    public UnAuthorizationException(ApiResponse apiResponse) {
        this.apiResponse = apiResponse;
    }
}
