package com.nguyendat.blog.payload.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String username;
    private String bio;
    private String avatar;
    private LocalDate createAt;
    private LocalDate updateAt;


    public UserResponse(Long id, String name, String username, String bio, LocalDate createAt, LocalDate updateAt) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.bio = bio;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }


}
