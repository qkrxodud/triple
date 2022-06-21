package triple.review.entitiy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point {
    @Id
    @GeneratedValue
    @Column(name = "seq")
    private int seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewId")
    private Review review;

    private String userUUID;

    @Column(name = "point_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PointStatus pointStatus;

    private int point;
    private String reason;
    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

    public static Point createPoint(Review review, String userUUID, PointStatus pointStatus,
                                    int point, String reason) {
        Point createPoint = new Point();
        createPoint.changePoint(review, userUUID, pointStatus, point, reason);
        return createPoint;
    }

    public void changePoint(Review review, String userUUID, PointStatus pointStatus,
                            int point, String reason) {
        this.review = review;
        this.userUUID = userUUID;
        this.pointStatus = pointStatus;
        this.point = point;
        this.reason = reason;
        this.regDate = LocalDateTime.now();
    }

}
