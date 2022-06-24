package triple.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import triple.review.entitiy.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer>, ReviewRepositoryCustom {
    Review findByReviewId(Long reviewId);
}
