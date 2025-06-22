package com.example.DATN.repositories;

import com.example.DATN.entities.CategoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryItemRepo extends JpaRepository<CategoryItem, Long> {
    List<CategoryItem> findByNameContainingIgnoreCase(String name);
    List<CategoryItem> findByCategoryId(Long categoryId);
    Page<CategoryItem> findAll(Pageable pageable);
}
