package com.backend.dream.service.imp;

import com.backend.dream.dto.FeedBackDTO;
import com.backend.dream.entity.FeedBack;
import com.backend.dream.mapper.FeedBackMapper;
import com.backend.dream.repository.FeedBackRepository;
import com.backend.dream.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImp implements FeedbackService {

    private final FeedBackRepository feedBackRepository;
    private final FeedBackMapper feedBackMapper;

    @Autowired
    public FeedbackServiceImp(FeedBackRepository feedBackRepository, FeedBackMapper feedBackMapper) {
        this.feedBackRepository = feedBackRepository;
        this.feedBackMapper = feedBackMapper;
    }

    @Override
    public List<FeedBackDTO> getFeedbacksForProduct(Long productId) {
        List<FeedBack> feedbackList = feedBackRepository.findFeedbacksByProductId(productId);
        return feedbackList.stream()
                .map(feedBackMapper::feedBackToFeedBackDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void save(FeedBackDTO feedbackDTO) {
        FeedBack feedback = feedBackMapper.feedBackDTOToFeedBack(feedbackDTO);
        feedBackRepository.save(feedback);
    }
}
