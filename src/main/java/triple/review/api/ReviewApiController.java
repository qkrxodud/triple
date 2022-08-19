package triple.review.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import triple.review.dto.ReviewDto;
import triple.review.entitiy.Review;
import triple.review.entitiy.ReviewStatus;
import triple.review.service.AttachmentService;
import triple.review.service.ReviewService;
import triple.review.utils.ResponseMessage;

import javax.validation.Valid;

import static triple.review.entitiy.Review.*;
import static triple.review.utils.DefaultRes.createDefaultRes;

@RestController
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewService reviewService;
    private final AttachmentService attachmentService;

    // 리뷰등록
    @PostMapping("/api/save-review")
    public ResponseEntity saveReview(@RequestBody @Valid CreateReviewRequest request) {
        Review review = createReview(request.getReviewId(), request.getUserId(), request.getPlaceId(),
                request.getContent(), ReviewStatus.ADD);

        //등록
        Review saveReview = reviewService.save(review, request.getAttachedPhotoIds());
        ReviewDto reviewDto = new ReviewDto(saveReview.getReviewUUID(), saveReview.getContent(), saveReview.getReviewStatus());

        return new ResponseEntity<>(createDefaultRes(ResponseMessage.OK,
                "SUCCESS", reviewDto), HttpStatus.OK);
    }

    // 리뷰 삭제
    @PostMapping("/api/delete-review")
    public ResponseEntity deleteReview(@RequestBody @Valid CreateDeleteReviewRequest request) {
        // 삭제
        Review deleteReview = reviewService.deleteReview(request.getReviewId(), request.getUserId(), request.getPlaceId(), request.getAction());
        ReviewDto reviewDto = new ReviewDto(deleteReview.getReviewUUID(), deleteReview.getContent(), deleteReview.getReviewStatus());

        return new ResponseEntity<>(createDefaultRes(ResponseMessage.OK,
                "SUCCESS", reviewDto), HttpStatus.OK);
    }

    // 리뷰 수정
    @PostMapping("/api/mod-review")
    public ResponseEntity modifyReview(@RequestBody @Valid CreateReviewRequest request) {
        Long reviewId = reviewService.modReview(request.getReviewId(), request.getUserId(),
                request.getPlaceId(), request.getAction(), request.getContent());
        Review findReview = reviewService.findByReviewId(reviewId);

        // 첨부 파일 변경
        attachmentService.modAttachment(findReview, request.getAttachedPhotoIds());

        ReviewDto reviewDto = new ReviewDto(findReview.getReviewUUID(), findReview.getContent(), findReview.getReviewStatus());
        return new ResponseEntity<>(createDefaultRes(ResponseMessage.OK,
                "SUCCESS", reviewDto), HttpStatus.OK);
    }

    @Data
    static class CreateReviewRequest {
        private String type;
        private ReviewStatus action;
        private String reviewId;
        private String content;
        private String[] attachedPhotoIds;
        private String userId;
        private String placeId;
    }

    @Data
    static class CreateDeleteReviewRequest {
        private String type;
        private ReviewStatus action;
        private String reviewId;
        private String userId;
        private String placeId;
    }
}
