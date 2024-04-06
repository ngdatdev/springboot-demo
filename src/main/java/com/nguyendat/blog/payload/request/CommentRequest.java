package com.nguyendat.blog.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentRequest {
    private String body;
    private String username;
}
