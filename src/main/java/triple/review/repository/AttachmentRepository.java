package triple.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import triple.review.entitiy.Attachment;

import java.util.List;
import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
    boolean existsByReview_ReviewIdAndReview_userUUID(Long reviewId, String userUUID);
    boolean existsByReview_ReviewId(Long reviewId);
    Optional<List<Attachment>> findByReview_ReviewId(Long reviewId);
    void deleteByReview_ReviewId(Long reviewId);
}
