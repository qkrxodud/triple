package triple.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PointDto {
    private String userUUID;
    private int plsPoint;
    private int minPoint;
    private int totalPoint;
}
