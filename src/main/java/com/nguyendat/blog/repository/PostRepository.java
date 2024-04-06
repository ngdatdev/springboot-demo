package com.nguyendat.blog.repository;

import com.nguyendat.blog.model.Category;
import com.nguyendat.blog.model.CategoryName;
import com.nguyendat.blog.model.PostBlog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostBlog, Long> {
    Optional<PostBlog> findById(Long id);

    List<PostBlog> findByCategoryName(CategoryName category);
}
