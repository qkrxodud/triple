package triple.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import triple.review.entitiy.Attachment;
import triple.review.entitiy.Point;
import triple.review.entitiy.PointStatus;
import triple.review.entitiy.Review;
import triple.review.repository.PointRepository;
import triple.review.repository.ReviewRepository;

import static triple.review.entitiy.Point.*;

/**
 * 사용자는 장소마다 리뷰를 1개만 작성할 수 있다.
 * 리뷰는 수정 또는 삭제할 수 있다.
 * 리뷰 작성 보상 점수는
 * 내용 점수
 *  1자 이상 텍스트 작성: 1점
 *  1장 이상 사진 첨부: 1점
 * 보너스 점수
 *  특정 장소에 첫 리뷰 작성 : 1점점
 *  */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PointRepository pointRepository;

    // 리뷰 등록
    @Transactional
    public Review save(Review review) {
        String placeUUID = review.getPlaceUUID();
        String userUUID = review.getUserUUID();
        String content = review.getContent();

        //사용자는 장소마다 리뷰를 1개만 작성 할 수 있다.
        boolean userReviewExists = reviewRepository.existsByUserUUIDAndPlaceUUID(userUUID, placeUUID);
        if (userReviewExists) {
            throw new IllegalArgumentException("이미 해당 장소에 리뷰를 작성하였습니다.");
        }

        // 특정 장소에 첫 리뷰 작성시 1점
        boolean placeReviewExists = reviewRepository.existsByPlaceUUID(placeUUID);
        if (placeReviewExists) {
            pointRepository.save(createPoint(review, userUUID, PointStatus.Y, 1, "first_review"));
        }

        // 1글자 이상 일때만 1점점
        if (content.length() > 0) {
            pointRepository.save(createPoint(review, userUUID, PointStatus.Y, 1, "review_reg"));
        }

        // 리뷰 등록
        Review saveReview = reviewRepository.save(review);
        return saveReview;
    }

    // 리뷰 삭제
    @Transactional
    public Review delete(Review review, Attachment attachment) {

        return null;
    }

    // 리뷰 수정
    @Transactional
    public Review update(Review review, Attachment attachment) {

        return null;
    }

}
