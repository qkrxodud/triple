package triple.review.entitiy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private int seq;

    @Column(name = "file_uuid")
    private String fileUUID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewId")
    private Review review;

    public static Attachment createAttachment(String fileUUID, Review review) {
        Attachment attachment = new Attachment();
        attachment.changeAttachment(fileUUID, review);
        return attachment;
    }

    public void changeAttachment(String fileUUID, Review review) {
        this.fileUUID = fileUUID;
        this.review = review;
    }
}
