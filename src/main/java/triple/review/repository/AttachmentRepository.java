package triple.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import triple.review.entitiy.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
    boolean existsByReview_ReviewIdAndReview_userUUID(Long reviewId, String userUUID);
}
