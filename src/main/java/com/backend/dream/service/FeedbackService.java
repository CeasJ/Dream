package com.backend.dream.service;

import com.backend.dream.dto.FeedBackDTO;

import java.util.List;

public interface FeedbackService {
    List<FeedBackDTO> getFeedbacksForProduct(Long productId);
    void save(FeedBackDTO feedbackDTO);
}
