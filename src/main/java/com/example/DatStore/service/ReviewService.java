package com.example.DatStore.service;

import com.example.DatStore.entity.Review;
import java.util.List;
import java.util.Optional;

public interface ReviewService {
    List<Review> getAllReviews();
    Optional<Review> getReviewById(Long id);
    List<Review> getReviewsByProductId(Long productId);
    Review createReview(Review review);
    void deleteReview(Long id);
}
