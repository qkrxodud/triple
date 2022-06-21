package triple.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReviewDto {
    private String reviewUUID;
    private String content;
    private List<AttachmentDto> Attachments;
}
