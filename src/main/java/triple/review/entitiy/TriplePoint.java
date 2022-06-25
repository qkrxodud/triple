package triple.review.entitiy;

import com.mysema.commons.lang.Assert;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TriplePoint extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private int seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewId")
    private Review review;

    @Column(name = "user_uuid")
    private String userUUID;

    @Column(name = "point_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PointStatus pointStatus;

    private int point;
    private String reason;
    private String returnReason;

    public static TriplePoint createPoint(Review review, String userUUID, PointStatus pointStatus,
                                    int point, String reason) {
        TriplePoint triplePoint = TriplePoint.builder()
                .review(review)
                .userUUID(userUUID)
                .pointStatus(pointStatus)
                .point(point)
                .reason(reason)
                .build();

        return triplePoint;
    }

    public void changePoint(PointStatus pointStatus, String returnReason) {
        this.pointStatus = pointStatus;
        this.returnReason = returnReason;
    }

    @Builder
    public TriplePoint(Review review, String userUUID, PointStatus pointStatus,
                       int point, String reason) {

        Assert.hasText(String.valueOf(review), "review must not be empty");
        Assert.hasText(userUUID, "userUUID must not be empty");
        Assert.hasText(String.valueOf(pointStatus), "pointStatus must not be empty");
        Assert.hasText(String.valueOf(point), "point must not be empty");
        Assert.hasText(reason, "reason must not be empty");

        this.review = review;
        this.userUUID = userUUID;
        this.pointStatus = pointStatus;
        this.point = point;
        this.reason = reason;
        this.createDateTime(LocalDateTime.now());
        this.changeModDate(LocalDateTime.now());

    }

}
