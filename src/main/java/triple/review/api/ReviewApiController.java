package triple.review.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import triple.review.dto.AttachmentDto;
import triple.review.dto.ReviewDto;
import triple.review.entitiy.Attachment;
import triple.review.entitiy.Review;
import triple.review.entitiy.ReviewStatus;
import triple.review.service.AttachmentService;
import triple.review.service.ReviewService;
import triple.review.utils.ResponseMessage;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import static triple.review.entitiy.Attachment.*;
import static triple.review.entitiy.Review.*;
import static triple.review.utils.DefaultRes.createDefaultRes;

@RestController
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewService reviewService;
    private final AttachmentService attachmentService;

    @PostMapping("/api/save-review")
    public ResponseEntity saveReview(@RequestBody @Valid CreateReviewRequest request) {
        // 리뷰 등록
        Review review = createReview(request.getReviewId(), request.getUserId(), request.getPlaceId(), request.getContent());
        Review saveReview = reviewService.save(review);

        // 파일등록
        String[] attachedPhotoIds = request.getAttachedPhotoIds();
        List<AttachmentDto> AttachmentDtos = new ArrayList<>();
        for (String fileUUID : attachedPhotoIds) {
            Attachment attachment = createAttachment(fileUUID, review);
            Attachment save = attachmentService.save(attachment);

            AttachmentDto attachmentDto = new AttachmentDto(save.getFileUUID());
            AttachmentDtos.add(attachmentDto);
        }

        ReviewDto reviewDto = new ReviewDto(saveReview.getReviewUUID(), saveReview.getContent(), AttachmentDtos);
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
}
