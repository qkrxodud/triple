package triple.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import triple.review.entitiy.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
}
