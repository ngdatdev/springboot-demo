package com.nguyendat.blog.controller;


import com.nguyendat.blog.model.role.Role;
import com.nguyendat.blog.model.role.RoleName;
import com.nguyendat.blog.model.user.User;
import com.nguyendat.blog.payload.request.LoginRequest;
import com.nguyendat.blog.payload.request.SignupRequest;
import com.nguyendat.blog.payload.response.JwtResponse;
import com.nguyendat.blog.payload.response.MessageResponse;
import com.nguyendat.blog.repository.RoleRepository;
import com.nguyendat.blog.repository.UserRepository;
import com.nguyendat.blog.security.UserDetailServiceImp;
import com.nguyendat.blog.security.UserDetailsImp;
import com.nguyendat.blog.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailServiceImp userDetailServiceImp;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;


    @GetMapping("/test")
    public User userDetails() {
        return userRepository.findByUsername("zxcl113").orElseThrow(() -> new RuntimeException("Not found user"));
    }

//    @GetMapping("/test2")
//    public UserDetailsImp roleName() {
//        List<GrantedAuthority> authorities = new SimpleGrantedAuthority(RoleName.ROLE_USER.name()).co;
//        return new UserDetailsImp("dat", "zxcl", roles);
//    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        String jwt = jwtUtils.generateJwtToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken"));
        }
        User user = new User(signupRequest.getUsername(), encoder.encode(signupRequest.getPassword()), signupRequest.getName());
        Role role = roleRepository.findByName(RoleName.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Role is not found"));
        Role role2 = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new RuntimeException("Role is not found"));
        Set<Role> roles = new HashSet();
        roles.add(role);
        roles.add(role2);
        user.setRoles(roles);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User register successfully"));
    }

}
