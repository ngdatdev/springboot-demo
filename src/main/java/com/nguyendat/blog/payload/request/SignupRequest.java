package com.nguyendat.blog.payload.request;

import lombok.Getter;

@Getter
public class SignupRequest {
    private String name;
    private String username;
    private String password;
}
