package spring.study.yummy.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.study.yummy.domain.RestaurantRepository;
import spring.study.yummy.domain.Review;
import spring.study.yummy.domain.ReviewRepository;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review addReview(Long restaurant_id, Review review) {
        review.setRestaurantId(restaurant_id);
        return reviewRepository.save(review);
    }
}
