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
public class Review extends BaseEntity {

    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private Long reviewId;

    @Column(name = "review_uuid")
    private String reviewUUID;
    @Column(name = "user_uuid")
    private String userUUID;
    @Column(name = "place_uuid")
    private String placeUUID;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")

    private ReviewStatus reviewStatus;

    private String content;

    public static Review createReview(String reviewUUID, String userUUID,
                                      String placeUUID, String content, ReviewStatus reviewStatus) {
        Review review = Review.builder()
                .reviewUUID(reviewUUID)
                .userUUID(userUUID)
                .placeUUID(placeUUID)
                .content(content)
                .reviewStatus(reviewStatus).build();
        return review;
    }

    @Builder
    public Review(String reviewUUID, String userUUID,
                  String placeUUID, String content, ReviewStatus reviewStatus) {

        Assert.hasText(reviewUUID, "reviewUUID must not be empty");
        Assert.hasText(userUUID, "userUUID must not be empty");
        Assert.hasText(placeUUID, "placeUUID must not be empty");
        Assert.hasText(content, "content must not be empty");
        Assert.hasText(String.valueOf(reviewStatus), "reviewStatus must not be empty");


        this.reviewUUID = reviewUUID;
        this.userUUID = userUUID;
        this.placeUUID = placeUUID;
        this.content = content;
        this.reviewStatus = reviewStatus;
        this.createDateTime(LocalDateTime.now());
        this.changeModDate(LocalDateTime.now());
    }

    public void changeStatus(ReviewStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public void changeContent(String content) {
        this.content = content;
    }
}
