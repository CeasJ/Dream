package com.backend.dream.repository;

import com.backend.dream.entity.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack,Long> {
    @Query("SELECT f FROM Feedback f WHERE f.product.id = :productId")
    List<FeedBack> findFeedbacksByProductId(@Param("productId") Long productId);
}
