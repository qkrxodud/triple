package triple.review.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import triple.review.entitiy.Review;
import triple.review.entitiy.ReviewStatus;

import java.util.Optional;

import static triple.review.entitiy.QReview.review;


@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom{

    @Autowired
    private final JPAQueryFactory queryFactory;

    @Override
    public Boolean existsReview(String userUUID, String placeUUID) {
        Integer fetchOne =  queryFactory
                .selectOne()
                .from(review)
                .where(review.userUUID.eq(userUUID),
                        review.placeUUID.eq(placeUUID),
                        review.reviewStatus.ne(ReviewStatus.DELETE))
                .fetchFirst();

        return fetchOne != null;
    }

    @Override
    public Boolean existsFirstReview(String placeUUID) {
        Integer fetchOne =  queryFactory
                .selectOne()
                .from(review)
                .where(review.placeUUID.eq(placeUUID),
                        review.reviewStatus.ne(ReviewStatus.DELETE))
                .fetchFirst();

        return fetchOne != null;
    }

    @Override
    public Optional<Review> findReview(String reviewUUID, String userUUID, String placeUUID) {
        Review findReview = queryFactory.select(review)
                .from(review)
                .where(review.reviewUUID.eq(reviewUUID),
                        review.userUUID.eq(userUUID),
                        review.placeUUID.eq(placeUUID),
                        review.reviewStatus.ne(ReviewStatus.DELETE)
                )
                .fetchOne();

        return Optional.ofNullable(findReview);
    }


}
