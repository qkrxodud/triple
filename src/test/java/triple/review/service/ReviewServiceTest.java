package triple.review.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import triple.review.entitiy.Review;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

import static triple.review.entitiy.Review.createReview;


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
        Review review1 = createReview("test", "test", "test", "좋아요!");
        Review review2 = createReview("test1", "test1", "test1", "좋아요!");
        Review review3 = createReview("test2", "test2", "test2", "좋아요!");
        Review review4 = createReview("test3", "test3", "test3", "좋아요!");


        //when
        Review saveReview1 = reviewService.save(review1);
        Review saveReview2 = reviewService.save(review2);
        Review saveReview3 = reviewService.save(review3);
        Review saveReview4 = reviewService.save(review4);


        //then
        assertThat(saveReview1).isEqualTo(review1);
        assertThat(saveReview2).isEqualTo(review2);
        assertThat(saveReview3).isEqualTo(review3);
        assertThat(saveReview4).isEqualTo(review4);
    }

    @Test
    public void 리뷰텍스트점수() {
        //given
        Review review1 = createReview("test", "test", "test", "좋아요!");
        Review review2 = createReview("test1", "test1", "test", "좋아요!");
        Review review3 = createReview("test2", "test2", "test", "좋아요!");
        Review review4 = createReview("test3", "test3", "test", "좋아요!");
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
        Review review1 = createReview("test", "test", "test", "좋아요!");

        //when
        List<Object[]> userPoint1 = pointService.findUserPoint(review1.getUserUUID());

        //then
        // 첫 리뷰
        Object[] objects1 = userPoint1.get(0);
        assertThat(Integer.parseInt(objects1[1].toString())).isEqualTo(2);
    }


}