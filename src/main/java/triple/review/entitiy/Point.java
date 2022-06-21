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

    @ManyToOne
    @JoinColumn(name = "reviewId")
    private Review review;

    private String userUUID;

    @Column(name = "point_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status pointStatus;

    private int pint;
    @Column(name = "reg_date", nullable = false)
    private LocalDateTime regDate;

}
