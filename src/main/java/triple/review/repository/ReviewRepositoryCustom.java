package triple.review.repository;

import triple.review.entitiy.Review;

import java.util.Optional;

public interface ReviewRepositoryCustom {
    Boolean existsReview(String userUUID, String placeUUID);
    Boolean existsFirstReview(String placeUUID);
    Optional<Review> findReview(String reviewUUID, String userUUID, String placeUUID);

}
