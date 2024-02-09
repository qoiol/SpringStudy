package spring.project.service;

import org.springframework.stereotype.Service;
import spring.project.domain.Review;
import spring.project.repository.LikeyRepository;
import spring.project.repository.ReviewRepository;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final LikeyRepository likeyRepository;

    public ReviewService(ReviewRepository reviewRepository, LikeyRepository likeyRepository) {
        this.reviewRepository = reviewRepository;
        this.likeyRepository = likeyRepository;
    }


    public long createReview(Review review){
        review.setLikeCount(0);
        reviewRepository.save(review);
        return review.getId();
    }

    public List<Review> findReviews(){
        return reviewRepository.findAll();
    }

    public void delete(long id){
        reviewRepository.deleteById(id);
    }
}
