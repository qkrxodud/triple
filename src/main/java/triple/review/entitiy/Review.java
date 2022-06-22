package triple.review.entitiy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long reviewId;

    @Column(name = "review_uuid")
    private String reviewUUID;
    @Column(name = "user_uuid")
    private String userUUID;
    @Column(name = "place_uuid")
    private String placeUUID;

    private String content;

    //TODO BaseEntitiy로 할지 고민..
    @Column(name = "reg_date")
    private LocalDateTime regDate;
    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

    public static Review createReview(String reviewUUID, String userUUID,
                                      String placeUUID, String content) {
        Review review = new Review();
        review.changeReview(reviewUUID, userUUID, placeUUID, content);
        return review;
    }

    public void changeReview(String reviewUUID, String userUUID,
                             String placeUUID, String content) {
        this.reviewUUID = reviewUUID;
        this.userUUID = userUUID;
        this.placeUUID = placeUUID;
        this.content = content;
        this.regDate = LocalDateTime.now();
        this.modifyDate = LocalDateTime.now();
    }

}
