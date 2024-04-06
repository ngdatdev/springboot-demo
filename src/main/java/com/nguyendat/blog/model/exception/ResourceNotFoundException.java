package com.nguyendat.blog.model.exception;

import com.nguyendat.blog.payload.response.ApiResponse;
import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException{
    private ApiResponse apiResponse;

    private String fieldName;
    private String sourceName;

    public ResourceNotFoundException(String fieldName, String sourceName) {
        this.sourceName = sourceName;
        this.fieldName = fieldName;
        setApiResponse();
    }

    public void setApiResponse() {
        String message = "This " + fieldName + " id " + sourceName + " is not found";
        apiResponse = new ApiResponse(Boolean.FALSE, message);
    }


}
