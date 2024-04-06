package com.nguyendat.blog.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nguyendat.blog.model.CommentPost;
import com.nguyendat.blog.model.PostBlog;
import com.nguyendat.blog.model.audit.DateAudit;
import com.nguyendat.blog.model.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    @JsonIgnore
    private String password;

    @NotBlank
    private String name;

    private String bio;

    @JsonIgnore
    private String avatar;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();



    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<PostBlog> postBlog;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<CommentPost> commentPost;

    public User(String username, String password, String name) {
        this.username = username;
        this.password = password;
        this.name = name;
    }
}
