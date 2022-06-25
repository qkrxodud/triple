package triple.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import triple.review.entitiy.PointStatus;
import triple.review.entitiy.TriplePoint;

import java.util.List;
import java.util.Optional;

public interface PointRepository  extends JpaRepository<TriplePoint, Integer> , PointRepositoryCustom{
    List<Optional<TriplePoint>> findPointByReview_ReviewIdAndPointStatus(Long reviewId, PointStatus pointStatus);

    @Query(value =
            "SELECT t_point.user_uuid, IFNULL(temp_point.point, 0) AS point " +
              "FROM (SELECT user_uuid " +
                      "FROM triple_point " +
                    " GROUP BY user_uuid) AS t_point " +
              "LEFT OUTER JOIN (SELECT user_uuid, IFNULL(SUM(point), 0) AS point " +
                                 "FROM triple_point " +
                                "WHERE point_status = ?2 " +
                                "GROUP BY user_uuid) AS temp_point ON temp_point.user_uuid = t_point.user_uuid " +
              "WHERE t_point.user_uuid = ?1 " ,

            nativeQuery = true)
    List<Object[]> userPointFindByUserUUID(String userUUId, String pointStatus);

    @Query(value =
            "SELECT t_point.user_uuid, IFNULL(temp_point.point, 0) AS point " +
              "FROM (SELECT user_uuid " +
                      "FROM triple_point " +
                    " GROUP BY user_uuid) AS t_point " +
              "LEFT OUTER JOIN (SELECT user_uuid, IFNULL(SUM(point), 0) AS point " +
                                 "FROM triple_point " +
                                "WHERE point_status = ?1 " +
                                "GROUP BY user_uuid) AS temp_point ON temp_point.user_uuid = t_point.user_uuid ",
            nativeQuery = true)
    List<Object[]> allPointFindByAll(String pointStatus);
}
