package triple.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import triple.review.entitiy.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

    boolean existsByUserUUIDAndPlaceUUID(String userUUID,String placeUUID);
    boolean existsByPlaceUUID(String placeUUID);
}
