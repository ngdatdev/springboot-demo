package com.nguyendat.blog.model.exception;


import com.nguyendat.blog.payload.response.ApiResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException extends RuntimeException{
    private ApiResponse apiResponse;

    public BadRequestException(ApiResponse apiResponse) {
        this.apiResponse = apiResponse;
    }

    public BadRequestException(String message) {
        super();
        apiResponse = new ApiResponse(Boolean.FALSE, message);
    }
}
