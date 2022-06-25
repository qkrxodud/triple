package triple.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import triple.review.entitiy.ReviewStatus;

@Data
@AllArgsConstructor
public class ReviewDto {

    private String reviewUUID;
    private String content;
    private ReviewStatus reviewStatus;

}
