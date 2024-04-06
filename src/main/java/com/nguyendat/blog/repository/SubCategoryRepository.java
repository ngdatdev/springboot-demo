package com.nguyendat.blog.repository;


import com.nguyendat.blog.model.SubCategory;
import com.nguyendat.blog.model.SubCategoryName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    Optional<SubCategory> findByName(SubCategoryName subCategoryName);
}
