package com.nguyendat.blog.model;

import com.nguyendat.blog.model.audit.UserDateAudit;
import com.nguyendat.blog.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentPost extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 1000)
    private String body;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "post_id")
    private PostBlog postBlog;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public CommentPost(String body, PostBlog postBlog, User user) {
        this.body = body;
        this.postBlog = postBlog;
        this.user = user;
    }
}
