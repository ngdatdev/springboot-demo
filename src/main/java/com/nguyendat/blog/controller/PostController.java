package com.nguyendat.blog.controller;

import com.nguyendat.blog.payload.request.PostBlogRequest;
import com.nguyendat.blog.payload.response.ApiResponse;
import com.nguyendat.blog.payload.response.PostBlogResponse;
import com.nguyendat.blog.security.CurrentUser;
import com.nguyendat.blog.security.UserDetailsImp;
import com.nguyendat.blog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/post")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<PostBlogResponse> getPostById(@PathVariable Long id) {
        return postService.getPostByIdPost(id);
    }

    @GetMapping("/course/{category}")
    public ResponseEntity<List<String>> getPostsByCategoryName(@PathVariable String category) {
        return postService.getAllPostByCategory(category);
    }

    @PostMapping("/create")
    public ResponseEntity<PostBlogResponse> createPost(@RequestBody PostBlogRequest postBlogRequest, @CurrentUser UserDetailsImp currentUser) {
        return postService.createPost(postBlogRequest, currentUser);
    }

    @DeleteMapping("/delete/{idPost}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Long idPost) {
        return postService.deletePost(idPost);
    }

    @GetMapping("/test")
    public String test(@RequestBody PostBlogRequest postBlogRequest) {
        return postBlogRequest.getCategory();
    }
}
