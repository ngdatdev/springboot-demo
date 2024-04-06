package com.nguyendat.blog.service;


import com.nguyendat.blog.model.CommentPost;
import com.nguyendat.blog.model.PostBlog;
import com.nguyendat.blog.model.exception.ResourceNotFoundException;
import com.nguyendat.blog.model.exception.UnAuthorizationException;
import com.nguyendat.blog.model.user.User;
import com.nguyendat.blog.payload.request.CommentRequest;
import com.nguyendat.blog.payload.response.ApiResponse;
import com.nguyendat.blog.payload.response.CommentResponse;
import com.nguyendat.blog.repository.CommentRepository;
import com.nguyendat.blog.repository.PostRepository;
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
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<List<CommentResponse>> getCommentPost(Long idPost) {
        PostBlog postBlog = postRepository.findById(idPost).orElseThrow(() -> new ResourceNotFoundException("Post", idPost.toString()));
        List<CommentPost> commentPosts = commentRepository.findByPostBlogId(idPost);

        if(commentPosts != null && !commentPosts.isEmpty()) {
            List<CommentResponse> commentResponses = commentPosts.stream().map((m) ->
                 new CommentResponse(idPost, m.getUser().getName(), m.getBody())
            ).collect(Collectors.toList());
            return new ResponseEntity<>(commentResponses, HttpStatus.OK);
        }
        throw new ResourceNotFoundException("PostBlog", idPost.toString());
    }

    public ResponseEntity<CommentResponse> addComment(CommentRequest commentRequest,Long idPost, UserDetailsImp currentUser) {
        User user = userRepository.findByUsername(currentUser.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User is not found"));
        PostBlog postBlog = postRepository.findById(idPost).orElseThrow(() -> new ResourceNotFoundException("Post", idPost.toString()));
        if(commentRequest.getUsername().equals(currentUser.getUsername())) {
            CommentPost commentPost = new CommentPost(commentRequest.getBody(), postBlog, user);
            commentRepository.save(commentPost);
            return new ResponseEntity<>(new CommentResponse(idPost, currentUser.getUsername(), commentRequest.getBody()), HttpStatus.CREATED);
        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "This user is not authorization");
        throw new UnAuthorizationException(apiResponse);
    }
}
