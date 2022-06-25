package triple.review.entitiy;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter

@MappedSuperclass
public class BaseEntity {

    @Column(name = "reg_date")
    private LocalDateTime createDateTime;
    @Column(name = "mod_date")
    private LocalDateTime lastModifyDateTime;

    public void createDateTime(LocalDateTime dateTime) {
        this.createDateTime = dateTime;
    }

    public void changeModDate(LocalDateTime dateTime) {
        lastModifyDateTime = dateTime;
    }
}
