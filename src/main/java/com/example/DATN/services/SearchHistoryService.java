package com.example.DATN.services;

import com.example.DATN.entities.SearchHistory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface SearchHistoryService {
    SearchHistory addSearchKeyword(Long userId, String keyword);
    Optional<SearchHistory> getSearchHistoryByUserId(Long userId);
    void clearSearchHistory(Long userId);
    boolean removeKeyword(Long userId, String keyword);
    List<SearchHistory> getAllSearchHistories();
}
