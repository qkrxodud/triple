package triple.review.entitiy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attachment {
    @Id
    @GeneratedValue
    @Column(name = "seq")
    private int seq;

    private String fileUUID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewId")
    private Review review;

    @Column(name = "reg_date")
    private LocalDateTime regDate;
    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

    public static Attachment createAttachment(String fileUUID, Review review) {
        Attachment attachment = new Attachment();
        attachment.changeAttachment(fileUUID, review);
        return attachment;
    }

    public void changeAttachment(String fileUUID, Review review) {
        this.fileUUID = fileUUID;
        this.review = review;
        this.regDate = LocalDateTime.now();
        this.modifyDate = LocalDateTime.now();
    }
}
