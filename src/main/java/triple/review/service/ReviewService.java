package triple.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import triple.review.entitiy.*;
import triple.review.exception.PointNotFoundException;
import triple.review.exception.ReviewNotFoundException;
import triple.review.repository.PointRepository;
import triple.review.repository.ReviewRepository;

import java.util.List;
import java.util.Optional;

import static triple.review.entitiy.Point.*;
import static triple.review.entitiy.ReviewStatus.*;

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
    public Long save(Review review) {
        String placeUUID = review.getPlaceUUID();
        String userUUID = review.getUserUUID();
        String content = review.getContent();

        if (isContentEmpty(content)) {
            throw new IllegalArgumentException("리뷰 내용을 작성해주세요.");
        }

        //사용자는 장소마다 리뷰를 1개만 작성 할 수 있다.
        boolean userReviewExists = reviewRepository.existsReview(userUUID, placeUUID);
        if (userReviewExists) {
            throw new IllegalArgumentException("이미 해당 장소에 리뷰를 작성하였습니다.");
        }

        // 특정 장소에 첫 리뷰 작성시 1점
        boolean placeReviewExists = reviewRepository.existsFirstReview(placeUUID);
        if (!placeReviewExists) {
            pointRepository.save(createPoint(review, userUUID, PointStatus.Y, 1, "first_review"));
        }

        // 리뷰 기본 점수 1점
        pointRepository.save(createPoint(review, userUUID, PointStatus.Y, 1, "review_reg"));

        // 리뷰 등록
        return reviewRepository.save(review).getReviewId();
    }

    // 리뷰 상태값 변경
    @Transactional
    public Long changeReviewStatus(String reviewUUID, String userUUID, String placeUUID, ReviewStatus reviewStatus, String content) {
        Review findReview = reviewRepository.findReview(reviewUUID, userUUID, placeUUID).
                orElseThrow(()->new IllegalArgumentException("삭제 혹은 수정 할 데이타가 없습니다."));

        switch (reviewStatus) {
            case DELETE:
                execDelete(findReview, reviewStatus);
                break;
            case MOD:
                execMod(findReview, content);
                break;
        }

        return findReview.getReviewId();
    }

    // 리뷰삭제
    @Transactional
    public void execDelete(Review findReview, ReviewStatus reviewStatus) {
        findReview.changeStatus(reviewStatus);
        deletePoint(findReview.getReviewId());
    }

    // 리뷰수정
    @Transactional
    public void execMod(Review findReview, String content) {
        if (isContentEmpty(content)) {
            throw new IllegalArgumentException("리뷰 내용을 작성해주세요.");
        }
        findReview.changeStatus(MOD);
        findReview.changeContent(content);
    }

    // 포인트 삭제
    @Transactional
    public void deletePoint(Long reviewId) {
        List<Optional<Point>> findPoints = pointRepository.findPointByReview_ReviewIdAndPointStatus(reviewId, PointStatus.Y);

        for (Optional<Point> point : findPoints) {
            Point findPoint = point.orElseThrow(PointNotFoundException::new);
            Point createPoint = createPoint(findPoint.getReview(), findPoint.getUserUUID(), PointStatus.N, findPoint.getPoint(), findPoint.getReason()+"_delete");
            pointRepository.save(createPoint);
        }
    }

    // 리뷰 찾기
    public Review findReview(String reviewUUID, String userUUID, String placeUUID) {
        return reviewRepository.findReview(reviewUUID, userUUID, placeUUID).orElseThrow(
                () -> new ReviewNotFoundException("리뷰를 찾을 수 없습니다."));
    }


    public Review findByReviewId(Long reviewId) {
        return reviewRepository.findByReviewId(reviewId);
    }

    private boolean isContentEmpty(String content) {
        return content.length() == 0;
    }

}
