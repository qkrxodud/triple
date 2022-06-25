package triple.review.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import triple.review.entitiy.TriplePoint;

import java.util.Optional;

import static triple.review.entitiy.QTriplePoint.*;


@RequiredArgsConstructor
public class PointRepositoryImpl implements PointRepositoryCustom{

    @Autowired
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<TriplePoint> findBonusPoint(Long reviewId) {
        TriplePoint findPoint = queryFactory.select(triplePoint)
                .from(triplePoint)
                .where(triplePoint.review.reviewId.eq(reviewId),
                        triplePoint.reason.eq("reg_img")).fetchOne();
        return Optional.ofNullable(findPoint);
    }
}
