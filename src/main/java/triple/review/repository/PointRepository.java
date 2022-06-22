package triple.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import triple.review.entitiy.Point;

import java.util.List;

public interface PointRepository  extends JpaRepository<Point, Integer> {

    @Query(value =
            "SELECT user_uuid, pls_point, min_point, pls_point - min_point AS total_point " +
              "FROM (SELECT p.user_uuid, IFNULL(pls_p.point, 0) AS pls_point, IFNULL(min_p.point, 0) AS min_point " +
                      "FROM point AS p " +
                "LEFT OUTER JOIN (SELECT user_uuid, SUM(point) AS point " +
                                   "FROM point " +
                                  "WHERE point_status = 'Y' " +
                                    "AND user_uuid = ?1 " +
                                  "GROUP BY user_uuid) AS pls_p ON p.user_uuid = pls_p.user_uuid " +
                "LEFT OUTER JOIN (SELECT user_uuid, SUM(point) AS point " +
                                   "FROM point " +
                                  "WHERE point_status = 'N' " +
                                    "AND user_uuid = ?1 " +
                                  "GROUP BY user_uuid) AS min_p ON p.user_uuid = min_p.user_uuid " +
              " GROUP BY user_uuid) AS point " +
              " WHERE user_uuid = ?1",
            nativeQuery = true)
    List<Object[]> userPointFindByUserUUID(String userUUId);

    @Query(value =
            "SELECT p.user_uuid, IFNULL(pls_p.point, 0) AS pls_point, IFNULL(min_p.point, 0) AS min_point " +
              "FROM point AS p " +
            "  LEFT OUTER JOIN (SELECT user_uuid, SUM(point) AS point " +
                    "FROM point " +
                    "WHERE point_status = 'Y' " +
                    "GROUP BY user_uuid) AS pls_p ON p.user_uuid = pls_p.user_uuid " +
            "  LEFT OUTER JOIN (SELECT user_uuid, SUM(point) AS point " +
                    "FROM point " +
                    "WHERE point_status = 'N' " +
                    "GROUP BY user_uuid) AS min_p ON p.user_uuid = min_p.user_uuid " +
                    " GROUP BY user_uuid ",
            nativeQuery = true)
    List<Object[]> allPointFindByAll();
}
