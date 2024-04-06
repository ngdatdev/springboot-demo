package com.nguyendat.blog.service;

import com.nguyendat.blog.model.*;
import com.nguyendat.blog.model.exception.ResourceNotFoundException;
import com.nguyendat.blog.model.user.User;
import com.nguyendat.blog.payload.request.PostBlogRequest;
import com.nguyendat.blog.payload.response.ApiResponse;
import com.nguyendat.blog.payload.response.PageResponse;
import com.nguyendat.blog.payload.response.PostBlogResponse;
import com.nguyendat.blog.repository.CategoryRepository;
import com.nguyendat.blog.repository.PostRepository;
import com.nguyendat.blog.repository.SubCategoryRepository;
import com.nguyendat.blog.repository.UserRepository;
import com.nguyendat.blog.security.UserDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    public ResponseEntity<PostBlogResponse> getPostByIdPost(Long id) {
        PostBlog postBlog = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("idPost", id.toString()));
        return new ResponseEntity<>(new PostBlogResponse(postBlog.getTitle(), postBlog.getBody(), postBlog.getChapter(), postBlog.getUpdateAt()), HttpStatus.OK);
    }

    public ResponseEntity<List<String>> getAllPostByCategory(String name) {
        CategoryName categoryName = convertStringToCategoryName(name);
        List<PostBlog> postBlogList = postRepository.findByCategoryName(categoryName);
        List<String> postTitles = postBlogList.stream().map(p -> {
            return p.getChapter() + " " + p.getTitle();
        }).collect(Collectors.toList());
        return new ResponseEntity<>(postTitles, HttpStatus.OK);
    }



    public ResponseEntity<PostBlogResponse> createPost(PostBlogRequest postBlogRequest, UserDetailsImp currentUser) {
        User user = userRepository.findByUsername(currentUser.getUsername()).orElseThrow(() -> new UsernameNotFoundException("This username " + currentUser.getUsername() + " is not found" ));
        CategoryName categoryName = convertStringToCategoryName(postBlogRequest.getCategory());
        SubCategoryName subCategoryName = convertStringToSubCategoryName(postBlogRequest.getSubCategory());
        Category category = categoryRepository.findByName(categoryName).orElseThrow(() -> new ResourceNotFoundException("Category", postBlogRequest.getCategory()));
        SubCategory subCategory = subCategoryRepository.findByName(subCategoryName).orElseThrow(() -> new ResourceNotFoundException("SubCategory", postBlogRequest.getSubCategory()));
        PostBlog postBlog = new PostBlog(postBlogRequest.getTitle(), postBlogRequest.getBody(), postBlogRequest.getChapter(), category, subCategory, user);
        PostBlog postBlogA = postRepository.save(postBlog);
        PostBlogResponse postBlogResponse = new PostBlogResponse(postBlogA.getTitle(), postBlogA.getBody(), postBlogRequest.getChapter(), postBlogA.getUpdateAt());
        return new ResponseEntity<>(postBlogResponse, HttpStatus.CREATED);
    }

    public ResponseEntity<ApiResponse> deletePost(Long id) {
        PostBlog postBlog = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("idPost", id.toString()));
        postRepository.delete(postBlog);
        return new ResponseEntity<>(new ApiResponse(Boolean.TRUE, "Delete post successfully"), HttpStatus.OK);
    }

    private CategoryName convertStringToCategoryName(String categoryString) {
        try {
            return CategoryName.valueOf(categoryString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Not found CategoryName: " + categoryString);
        }
    }

    private SubCategoryName convertStringToSubCategoryName(String subCategoryString) {
        try {
            return SubCategoryName.valueOf(subCategoryString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Not found SubCategoryName " + subCategoryString);
        }
    }
}
