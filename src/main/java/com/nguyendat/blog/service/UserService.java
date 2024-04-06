package com.nguyendat.blog.service;


import com.nguyendat.blog.model.exception.BadRequestException;
import com.nguyendat.blog.model.exception.UnAuthorizationException;
import com.nguyendat.blog.model.user.User;
import com.nguyendat.blog.payload.request.UserProfileRequest;
import com.nguyendat.blog.payload.response.ApiResponse;
import com.nguyendat.blog.payload.response.PageResponse;
import com.nguyendat.blog.payload.response.UserResponse;
import com.nguyendat.blog.repository.UserRepository;
import com.nguyendat.blog.security.UserDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public String getCurrentUser(UserDetailsImp userDetailsImp) {
        return userDetailsImp.getUsername();
    }

    //not yet test
    public ResponseEntity<PageResponse<User>> getALlUser(int page, int size) {
        validatePageNumberSize(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "CreateAt");
        Page<User> users = userRepository.findAll(pageable);
        List<User> contents = users.getNumberOfElements() == 0 ? Collections.emptyList() : Collections.unmodifiableList(users.getContent());
        PageResponse<User> userResponsePageResponse = new PageResponse<>(contents, users.getNumber(), users.getSize(),
                users.getTotalElements(), users.getTotalPages(), users.isLast());
        return new ResponseEntity<PageResponse<User>>(userResponsePageResponse, HttpStatus.OK);
    }

    public ResponseEntity<UserResponse> getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + " is not found"));
        UserResponse userResponse = new UserResponse(user.getId(),user.getName(), username, user.getBio(), user.getCreateAt(), user.getUpdateAt());
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    public ResponseEntity<?> deleteUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Username is not found!"));
        userRepository.delete(user);
        return ResponseEntity.ok("Delete username" + username + " successfully");
    }

    public ResponseEntity<UserResponse> updateProfileUser(UserProfileRequest userProfileRequest,String username, UserDetailsImp userDetailsImp) {
        User user = userRepository.findByUsername(userDetailsImp.getUsername()).orElseThrow(() -> new RuntimeException("Username is not found"));
        if(user.getUsername().equals(username)) {
            user.setName(userProfileRequest.getName());
            user.setBio(userProfileRequest.getBio());
            userRepository.save(user);
            return new ResponseEntity<>(new UserResponse(user.getId(), user.getName(),user.getUsername(), user.getBio(), user.getCreateAt(), user.getUpdateAt()), HttpStatus.OK);
        }
        ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "This user" + userDetailsImp.getUsername() + " can not edit this profile of " + username);
        throw new UnAuthorizationException(apiResponse);
    }

    private void validatePageNumberSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }
        if(size < 0) {
            throw new BadRequestException("Size number cannot be less than zero.");
        }
        if (size > 30) {
            throw new BadRequestException("Page size must not be greater than 30");
        }
    }

}
