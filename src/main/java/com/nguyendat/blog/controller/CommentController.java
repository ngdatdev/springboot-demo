package com.nguyendat.blog.controller;


import com.nguyendat.blog.payload.request.CommentRequest;
import com.nguyendat.blog.payload.response.CommentResponse;
import com.nguyendat.blog.security.CurrentUser;
import com.nguyendat.blog.security.UserDetailsImp;
import com.nguyendat.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/createComment/{idPost}")
    private ResponseEntity<CommentResponse> commentPost(@RequestBody CommentRequest commentRequest, @PathVariable Long idPost, @CurrentUser UserDetailsImp userDetailsImp) {
        return commentService.addComment(commentRequest, idPost, userDetailsImp);
    }
}
