package triple.review.entitiy;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name="ATTACHMENT_SEQ_GENERATOR",
        sequenceName = "ATTACHMENT_SEQ"
)
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "seq")
    private int seq;

    private String fileUUID;

    @ManyToOne
    @JoinColumn(name = "reviewId")
    private Review review;

    //TODO BaseEntitiy로 할지 고민..
    @Column(name = "reg_date")
    private LocalDateTime regDate;
    @Column(name = "modify_date")
    private LocalDateTime modifyDate;
}
