package triple.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import triple.review.entitiy.ReviewStatus;

import java.util.List;

@Data
@AllArgsConstructor
public class ReviewDto {

    private String reviewUUID;
    private String content;
    private List<AttachmentDto> Attachments;
    private ReviewStatus reviewStatus;

    public ReviewDto(String reviewUUID, String content, ReviewStatus reviewStatus) {
        this.reviewUUID = reviewUUID;
        this.content = content;
        this.reviewStatus = reviewStatus;
    }
}
