package com.nguyendat.blog.repository;

import com.nguyendat.blog.model.Category;
import com.nguyendat.blog.model.CategoryName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(CategoryName categoryName);


}
