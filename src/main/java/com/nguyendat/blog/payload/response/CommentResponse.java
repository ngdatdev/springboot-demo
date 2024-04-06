package com.nguyendat.blog.payload.response;

import lombok.Getter;

@Getter
public class CommentResponse {
    private Long idPost;
    private String nameUser;
    private String body;

    public CommentResponse(Long idPost, String nameUser, String body) {
        this.idPost = idPost;
        this.nameUser = nameUser;
        this.body = body;
    }
}
