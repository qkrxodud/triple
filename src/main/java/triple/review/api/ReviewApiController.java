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


    @PostMapping("/api/exec-review")
    public ResponseEntity execReview(@RequestBody @Valid CreateReviewRequest request) {
        ReviewDto reviewDto = null;
        switch (request.getAction()) {
            case ADD :
                reviewDto = saveReview(request);
                break;
            case MOD :
                modifyReview(request);
                break;
            case DELETE:
                reviewDto = deleteReview(request);
                break;

        }
        return new ResponseEntity<>(createDefaultRes(ResponseMessage.OK,
                "SUCCESS", reviewDto), HttpStatus.OK);
    }

    // 리뷰등록
    public ReviewDto saveReview(CreateReviewRequest request) {
        Review review = createReview(request.getReviewId(), request.getUserId(), request.getPlaceId(),
                request.getContent(), ReviewStatus.ADD);
        Long saveReviewId = reviewService.save(review);
        Review findReview = reviewService.findByReviewId(saveReviewId);

        // 첨부파일 등록
        attachmentService.saves(findReview, request.getAttachedPhotoIds());

        return new ReviewDto(findReview.getReviewUUID(), findReview.getContent(), AttachmentDtos, findReview.getReviewStatus());
    }

    // 리뷰 삭제
    public ReviewDto deleteReview(CreateReviewRequest request) {
        Long reviewId = reviewService.changeReviewStatus(request.getReviewId(), request.getUserId(),
                request.getPlaceId(), request.getAction(), request.getContent());
        Review findReview = reviewService.findByReviewId(reviewId);

        //첨부 파일 삭제
        attachmentService.deleteAttachment(findReview);

        return new ReviewDto(findReview.getReviewUUID(), findReview.getContent(), findReview.getReviewStatus());
    }

    // 리뷰 수정
    public Long modifyReview(CreateReviewRequest request) {
        Long reviewId = reviewService.changeReviewStatus(request.getReviewId(), request.getUserId(),
                request.getPlaceId(), request.getAction(), request.getContent());
        Review findReview = reviewService.findByReviewId(reviewId);

        // 첨부 파일 변경
        attachmentService.changeAttachment(findReview, request.getAttachedPhotoIds());
        // TODO dto로 만들어 주자
        return null;
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
}
