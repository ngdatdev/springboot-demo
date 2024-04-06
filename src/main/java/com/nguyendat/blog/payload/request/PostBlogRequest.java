package com.nguyendat.blog.payload.request;

import lombok.Data;

@Data
public class PostBlogRequest {
    private String title;
    private String body;
    private String chapter;
    private String category;
    private String subCategory;
}
