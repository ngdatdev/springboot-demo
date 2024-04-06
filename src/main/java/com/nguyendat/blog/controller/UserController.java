package com.nguyendat.blog.controller;

import com.nguyendat.blog.config.Constant;
import com.nguyendat.blog.model.user.User;
import com.nguyendat.blog.payload.request.UserProfileRequest;
import com.nguyendat.blog.payload.response.PageResponse;
import com.nguyendat.blog.payload.response.UserResponse;
import com.nguyendat.blog.security.CurrentUser;
import com.nguyendat.blog.security.UserDetailsImp;
import com.nguyendat.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @GetMapping("/me")
    public String getMe(@AuthenticationPrincipal UserDetailsImp userDetailsImp) {
        return userDetailsImp.getUsername();
    }

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public ResponseEntity<PageResponse<User>> getAllUsers(
            @RequestParam(value = "page", defaultValue = Constant.DEFAULT_PAGE_NUMBER, required = false) int page,
            @RequestParam(value = "size", defaultValue = Constant.DEFAULT_PAGE_SIZE, required = false) int size
    ) {
        return userService.getALlUser(page, size);
    }

    @GetMapping("/me/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }


    @DeleteMapping("delete/{username}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        return userService.deleteUserByUsername(username);
    }

    @PutMapping("update/{username}")
    public ResponseEntity<UserResponse> updateProfileUser(@RequestBody UserProfileRequest userProfileRequest, @PathVariable String username
    , @CurrentUser UserDetailsImp currentUser) {
        return userService.updateProfileUser(userProfileRequest, username, currentUser);
    }


}
