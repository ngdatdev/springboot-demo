package com.nguyendat.blog.model;

import com.nguyendat.blog.model.audit.UserDateAudit;
import com.nguyendat.blog.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostBlog extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    @Lob
    @Column(length = 1000)
    private String body;

    private boolean ready = true;

    private String chapter;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private SubCategory subCategory;

    @OneToMany(mappedBy = "postBlog", cascade = CascadeType.REMOVE)
    private List<CommentPost> comments;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public PostBlog(String title, String body, String chapter,Category category, SubCategory subCategory, User user) {
        this.title = title;
        this.body = body;
        this.chapter=chapter;
        this.category = category;
        this.subCategory = subCategory;
        this.user = user;
    }
}
