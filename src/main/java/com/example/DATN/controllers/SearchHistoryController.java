package com.example.DATN.controllers;

import com.example.DATN.entities.SearchHistory;
import com.example.DATN.services.SearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/search-history")
public class SearchHistoryController {

    private final SearchHistoryService searchHistoryService;

    @Autowired
    public SearchHistoryController(SearchHistoryService searchHistoryService) {
        this.searchHistoryService = searchHistoryService;
    }

    /**
     * Thêm từ khóa tìm kiếm mới
     */
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping("/add")
    public ResponseEntity<SearchHistory> addSearchKeyword(
            @RequestParam Long userId,
            @RequestParam String keyword) {
        try {
            SearchHistory updatedHistory = searchHistoryService.addSearchKeyword(userId, keyword);
            return ResponseEntity.ok(updatedHistory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Lấy lịch sử tìm kiếm của một người dùng
     */
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @GetMapping("/{userId}")
    public ResponseEntity<SearchHistory> getSearchHistory(@PathVariable Long userId) {
        Optional<SearchHistory> history = searchHistoryService.getSearchHistoryByUserId(userId);
        return history.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Xóa toàn bộ lịch sử tìm kiếm của một người dùng
     */
    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Void> clearSearchHistory(@PathVariable Long userId) {
        searchHistoryService.clearSearchHistory(userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Xóa một từ khóa  khỏi lịch sử tìm kiếm
     */
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping("/{userId}/keyword")
    public ResponseEntity<Map<String, Object>> removeKeyword(
            @PathVariable Long userId,
            @RequestParam String keyword) {
        try {
            boolean removed = searchHistoryService.removeKeyword(userId, keyword);

            if (removed) {
                Map<String, Object> response = new HashMap<>();
                response.put("removedKeyword", keyword);
                response.put("message", "Đã xóa từ khóa thành công");
                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Không tìm thấy từ khóa trong lịch sử tìm kiếm");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (IllegalArgumentException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Lấy tất cả lịch sử tìm kiếm
     */
    @GetMapping("/all")
    public ResponseEntity<List<SearchHistory>> getAllSearchHistories() {
        List<SearchHistory> histories = searchHistoryService.getAllSearchHistories();
        return ResponseEntity.ok(histories);
    }

    /**
     * Phân tích lịch sử tìm kiếm của một người dùng
     */
    @GetMapping("/{userId}/keywords")
    public ResponseEntity<Map<String, Object>> getSearchKeywords(@PathVariable Long userId) {
        Optional<SearchHistory> historyOpt = searchHistoryService.getSearchHistoryByUserId(userId);

        if (historyOpt.isPresent()) {
            SearchHistory history = historyOpt.get();
            String text = history.getText();

            if (text == null || text.isEmpty()) {
                return ResponseEntity.ok(Map.of(
                        "userId", userId,
                        "keywords", new String[0],
                        "count", 0
                ));
            } else {
                String[] keywords = text.split(" , ");
                return ResponseEntity.ok(Map.of(
                        "userId", userId,
                        "keywords", keywords,
                        "count", keywords.length
                ));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
