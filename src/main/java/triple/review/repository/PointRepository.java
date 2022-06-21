package triple.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import triple.review.entitiy.Point;

public interface PointRepository  extends JpaRepository<Point, Integer> {
}
