package triple.review.repository;

import triple.review.entitiy.TriplePoint;

import java.util.Optional;

public interface PointRepositoryCustom {

    Optional<TriplePoint> findBonusPoint(Long reviewId);
}
