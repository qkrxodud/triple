package triple.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import triple.review.entitiy.Attachment;
import triple.review.entitiy.Point;
import triple.review.entitiy.PointStatus;
import triple.review.entitiy.Review;
import triple.review.exception.FileNotFoundException;
import triple.review.repository.AttachmentRepository;
import triple.review.repository.PointRepository;

import java.util.List;

import static triple.review.entitiy.Attachment.createAttachment;
import static triple.review.entitiy.Point.createPoint;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final PointRepository pointRepository;

    // 파일 등록
    @Transactional
    public void saves(Review review, String [] fileUUIDs) {
        for (String fileUUID : fileUUIDs) {
            Attachment attachment = createAttachment(fileUUID, review);
            save(attachment);
        }
    }

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

    // 리뷰ID로 파일찾기
    public List<Attachment> findByReviewId(Long reviewId) {
        return attachmentRepository.findByReview_ReviewId(reviewId).orElseThrow(FileNotFoundException::new);
    }

    // 리뷰 파일 변경하기
    @Transactional
    public void changeAttachment(Review review, String [] fileUUIDs) {
        // 기존에 첨부파일이 존재 했는지
        boolean existsPreFile = attachmentRepository.existsByReview_ReviewId(review.getReviewId());

        // 이전파일 O & 업데이트 파일 X
        if (existsPreFile && !existsNowFile(fileUUIDs)) {
            attachmentRepository.deleteByReview_ReviewId(review.getReviewId());
            Point createPoint = createPoint(review, review.getUserUUID(), PointStatus.N, 1, "img_reg_delete");
            pointRepository.save(createPoint);
        // 이전파일 X & 업데이트 파일 O
        } else if (!existsPreFile && existsNowFile(fileUUIDs)) {
            for (String fileUUID : fileUUIDs) {
                Attachment attachment = createAttachment(fileUUID, review);
                save(attachment);
            }
        // 이전파일 O & 업데이트 파일 O
        } else if (existsPreFile && existsNowFile(fileUUIDs)) {
            attachmentRepository.deleteByReview_ReviewId(review.getReviewId());
            for (String fileUUID : fileUUIDs) {
                Attachment attachment = createAttachment(fileUUID, review);
                attachmentRepository.save(attachment);
            }

        }
    }

    @Transactional
    public void deleteAttachment(Review review) {
        attachmentRepository.deleteByReview_ReviewId(review.getReviewId());
    }

    boolean existsNowFile(String [] fileUUIDs) {
        boolean result = false;
        if (fileUUIDs.length > 0) {
            result = true;
        }
        return result;
    }

}
