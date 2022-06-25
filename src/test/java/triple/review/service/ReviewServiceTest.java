package triple.review.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import triple.review.entitiy.Review;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

import static triple.review.entitiy.Review.createReview;
import static triple.review.entitiy.ReviewStatus.*;


@SpringBootTest
@Transactional
class ReviewServiceTest {

    @Autowired
    ReviewService reviewService;

    @Autowired
    PointService pointService;

    @Test
    public void 리뷰등록() {
        //given
        Review review1 = createReview("test", "test", "test", "좋아요!", ADD);
        Review review2 = createReview("test1", "test1", "test1", "좋아요!", ADD);
        Review review3 = createReview("test2", "test2", "test2", "좋아요!", ADD);
        Review review4 = createReview("test3", "test3", "test3", "좋아요!", ADD);

        //when
        Long saveReviewId1 = reviewService.save(review1);
        Long saveReviewId2 = reviewService.save(review2);
        Long saveReviewId3 = reviewService.save(review3);
        Long saveReviewId4 = reviewService.save(review4);

        Review findReview1 = reviewService.findByReviewId(saveReviewId1);
        Review findReview2 = reviewService.findByReviewId(saveReviewId2);
        Review findReview3 = reviewService.findByReviewId(saveReviewId3);
        Review findReview4 = reviewService.findByReviewId(saveReviewId4);

        //then
        assertThat(findReview1.getReviewUUID()).isEqualTo("test");
        assertThat(findReview2.getReviewUUID()).isEqualTo("test1");
        assertThat(findReview3.getReviewUUID()).isEqualTo("test2");
        assertThat(findReview4.getReviewUUID()).isEqualTo("test3");
    }

    @Test
    public void 리뷰텍스트점수() {
        //given
        Review review1 = createReview("test", "test", "test", "좋아요!", ADD);
        Review review2 = createReview("test1", "test1", "test", "좋아요!", ADD);
        Review review3 = createReview("test2", "test2", "test", "좋아요!", ADD);
        Review review4 = createReview("test3", "test3", "test", "좋아요!", ADD);
        reviewService.save(review1);
        reviewService.save(review2);
        reviewService.save(review3);
        reviewService.save(review4);

        //when
        List<Object[]> userPoint1 = pointService.findUserPoint(review1.getUserUUID());
        List<Object[]> userPoint2 = pointService.findUserPoint(review2.getUserUUID());
        List<Object[]> userPoint3 = pointService.findUserPoint(review3.getUserUUID());
        List<Object[]> userPoint4 = pointService.findUserPoint(review4.getUserUUID());

        //then
        Object[] objects2 = userPoint2.get(0);
        assertThat(Integer.parseInt(objects2[1].toString())).isEqualTo(1);

        Object[] objects3 = userPoint3.get(0);
        assertThat(Integer.parseInt(objects3[1].toString())).isEqualTo(1);

        Object[] objects4 = userPoint4.get(0);
        assertThat(Integer.parseInt(objects4[1].toString())).isEqualTo(1);
    }

    @Test
    public void 리뷰보너스점수() {
        //given
        Review review1 = createReview("test", "test", "test", "좋아요!", ADD);
        reviewService.save(review1);

        //when
        List<Object[]> userPoint1 = pointService.findUserPoint(review1.getUserUUID());

        //then
        // 첫 리뷰
        Object[] objects1 = userPoint1.get(0);
        assertThat(Integer.parseInt(objects1[1].toString())).isEqualTo(2);
    }

    @Test
    public void 리뷰찾기() throws Exception {
        //given
        Review review1 = createReview("test", "test", "test", "좋아요!", ADD);
        reviewService.save(review1);

        //when
        Review findReview = reviewService.findReview("test", "test", "test");

        //then
        assertThat(findReview.getReviewUUID()).isEqualTo(review1.getReviewUUID());
    }

    @Test
    public void 리뷰삭제하기() {
        //given
        Review createReview = createReview("reviewUUIDtest12", "test2", "test2", "좋아요!", ADD);
        reviewService.save(createReview);

        //when
        Long reviewId = reviewService.deleteReview("reviewUUIDtest12", "test2", "test2", DELETE);
        Review findReview = reviewService.findByReviewId(reviewId);

        //then
        assertThat(findReview.getReviewStatus()).isEqualTo(DELETE);
    }

    @Test
    public void 리뷰삭제시포인트삭제() {
        //given
        Review createReview = createReview("reviewUUIDtest12", "test2", "test2", "좋아요!", ADD);
        reviewService.save(createReview);
        List<Object[]> userPoint1 = pointService.findUserPoint("test2");

        assertThat(Integer.parseInt(userPoint1.get(0)[1].toString())).isEqualTo(2);


        //when
        Long reviewId = reviewService.deleteReview("reviewUUIDtest12", "test2", "test2", DELETE);
        Review findReview = reviewService.findByReviewId(reviewId);

        List<Object[]> userPoint2 = pointService.findUserPoint("test2");


        //then
        assertThat(Integer.parseInt(userPoint2.get(0)[1].toString())).isEqualTo(0);
    }

    @Test
    public void 리뷰수정하기(){
        //given
        Review createReview = createReview("reviewUUIDtest12", "test2", "test2", "좋아요!", ADD);
        reviewService.save(createReview);

        //when
        Long reviewId = reviewService.modReview("reviewUUIDtest12", "test2", "test2", MOD, "좋은가요?");
        Review findReview = reviewService.findByReviewId(reviewId);

        //then
        assertThat(findReview.getReviewStatus()).isEqualTo(MOD);

    }
}