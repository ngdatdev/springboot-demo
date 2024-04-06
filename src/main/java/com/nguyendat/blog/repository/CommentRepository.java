package com.nguyendat.blog.repository;

import com.nguyendat.blog.model.CommentPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentPost, Long> {
    List<CommentPost> findByPostBlogId(Long id);
}
