package triple.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import triple.review.entitiy.Attachment;
import triple.review.entitiy.PointStatus;
import triple.review.entitiy.Review;
import triple.review.entitiy.TriplePoint;
import triple.review.exception.Eceptions.FileNotFoundException;
import triple.review.exception.Eceptions.PointNotFoundException;
import triple.review.repository.AttachmentRepository;
import triple.review.repository.PointRepository;

import java.time.LocalDateTime;
import java.util.List;

import static triple.review.entitiy.Attachment.createAttachment;
import static triple.review.entitiy.TriplePoint.createPoint;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final PointRepository pointRepository;

    // 파일들 등록
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
            pointRepository.save(createPoint(review, review.getUserUUID(), PointStatus.ADD, 1, "reg_img"));
        }

        return attachmentRepository.save(attachment);
    }

    // 리뷰ID로 파일찾기
    public List<Attachment> findByReviewId(Long reviewId) {
        return attachmentRepository.findByReview_ReviewId(reviewId).orElseThrow(FileNotFoundException::new);
    }

    // 파일 변경하기
    @Transactional
    public void modAttachment(Review review, String [] fileUUIDs) {
        // 기존에 첨부파일이 존재 했는지
        Long reviewId = review.getReviewId();
        boolean existsPreFile = attachmentRepository.existsByReview_ReviewId(reviewId);

        // 이전파일 O & 업데이트 파일 X
        if (existsPreFile && !existsNowFile(fileUUIDs)) {
            TriplePoint findPoint = pointRepository.findBonusPoint(reviewId).orElseThrow(() -> new PointNotFoundException("포인트를 찾을수 없습니다."));
            findPoint.changeModDate(LocalDateTime.now());
            findPoint.changePoint(PointStatus.DELETE, findPoint.getReason()+"_delete");

            attachmentRepository.deleteByReview_ReviewId(reviewId);
        // 이전파일 X & 업데이트 파일 O
        } else if (!existsPreFile && existsNowFile(fileUUIDs)) {
            for (String fileUUID : fileUUIDs) {
                Attachment attachment = createAttachment(fileUUID, review);
                save(attachment);
            }
        // 이전파일 O & 업데이트 파일 O
        } else if (existsPreFile && existsNowFile(fileUUIDs)) {
            attachmentRepository.deleteByReview_ReviewId(reviewId);
            for (String fileUUID : fileUUIDs) {
                Attachment attachment = createAttachment(fileUUID, review);
                attachmentRepository.save(attachment);
            }

        }
    }

    // 파일 삭제
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
