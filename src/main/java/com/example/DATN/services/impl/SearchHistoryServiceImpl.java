package com.example.DATN.services.impl;

import com.example.DATN.entities.SearchHistory;
import com.example.DATN.repositories.SearchHistoryRepository;
import com.example.DATN.entities.User;
import com.example.DATN.repositories.UserRepo;
import com.example.DATN.services.SearchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class SearchHistoryServiceImpl implements SearchHistoryService {

    private static final int MAX_HISTORY_SIZE = 10;
    private static final String KEYWORD_SEPARATOR = " , ";

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    @Autowired
    private UserRepo userRepo;

    @Override
    @Transactional
    public SearchHistory addSearchKeyword(Long userId, String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Từ khóa tìm kiếm không được để trống");
        }

        Optional<SearchHistory> existingHistory = searchHistoryRepository.findByUserId(userId);

        if (existingHistory.isPresent()) {
            SearchHistory history = existingHistory.get();
            String currentText = history.getText();

            List<String> keywords = new ArrayList<>();
            if (currentText != null && !currentText.isEmpty()) {
                keywords = new ArrayList<>(Arrays.asList(currentText.split(KEYWORD_SEPARATOR)));
            }

            // Nếu từ khóa đã tồn tại, loại bỏ vị trí cũ
            keywords.removeIf(k -> k.equalsIgnoreCase(keyword.trim()));

            // Thêm từ khóa mới vào cuối danh sách
            keywords.add(keyword.trim());

            // Nếu số lượng từ khóa vượt quá giới hạn, loại bỏ từ khóa đầu tiên
            if (keywords.size() > MAX_HISTORY_SIZE) {
                keywords.remove(0);
            }

            // Cập nhật lại text
            history.setText(String.join(KEYWORD_SEPARATOR, keywords));
            return searchHistoryRepository.save(history);

        } else {

            SearchHistory newHistory = new SearchHistory(userId, keyword.trim());
            return searchHistoryRepository.save(newHistory);
        }
    }


    @Override
    public Optional<SearchHistory> getSearchHistoryByUserId(Long userId) {
        return searchHistoryRepository.findByUserId(userId);
    }

    @Override
    @Transactional
    public void clearSearchHistory(Long userId) {
        Optional<SearchHistory> existingHistory = searchHistoryRepository.findByUserId(userId);

        existingHistory.ifPresent(history -> {
            history.setText("");
            searchHistoryRepository.save(history);
        });
    }

    @Override
    @Transactional
    public boolean removeKeyword(Long userId, String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Từ khóa cần xóa không được để trống");
        }

        Optional<SearchHistory> existingHistory = searchHistoryRepository.findByUserId(userId);

        if (existingHistory.isPresent()) {
            SearchHistory history = existingHistory.get();
            String currentText = history.getText();

            if (currentText == null || currentText.isEmpty()) {
                return false;  // Không có từ khóa nào để xóa
            }

            String[] keywords = currentText.split(KEYWORD_SEPARATOR);
            if (keywords.length == 0) {
                return false;
            }

            // Tạo danh sách mới không có từ khóa cần xóa
            List<String> updatedKeywords = new ArrayList<>();
            boolean found = false;

            for (String k : keywords) {
                if (k.equals(keyword)) {
                    found = true;
                } else {
                    updatedKeywords.add(k);
                }
            }

            if (!found) {
                return false; // Không tìm thấy từ khóa cần xóa
            }

            // Cập nhật lại text
            history.setText(String.join(KEYWORD_SEPARATOR, updatedKeywords));
            searchHistoryRepository.save(history);

            return true;
        }

        return false;
    }

    @Override
    public List<SearchHistory> getAllSearchHistories() {
        return searchHistoryRepository.findAll();
    }
}
