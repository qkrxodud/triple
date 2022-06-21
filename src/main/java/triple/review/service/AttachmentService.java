package triple.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import triple.review.entitiy.Attachment;
import triple.review.entitiy.PointStatus;
import triple.review.entitiy.Review;
import triple.review.repository.AttachmentRepository;
import triple.review.repository.PointRepository;

import static triple.review.entitiy.Point.createPoint;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final PointRepository pointRepository;

    // 파일 등록
    @Transactional
    public Attachment save(Attachment attachment) {
        Review review = attachment.getReview();

        // 사진 업로드 보너스 1점
        boolean exists = attachmentRepository.existsByReview_ReviewIdAndReview_userUUID(review.getReviewId(), review.getUserUUID());
        if (!exists) {
            pointRepository.save(createPoint(review, review.getUserUUID(), PointStatus.Y, 1, "img_reg"));
        }

        return attachmentRepository.save(attachment);
    }
}
