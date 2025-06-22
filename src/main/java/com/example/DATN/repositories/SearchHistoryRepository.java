package com.example.DATN.repositories;

import com.example.DATN.entities.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {


    Optional<SearchHistory> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
