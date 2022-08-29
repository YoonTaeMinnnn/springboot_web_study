package study.datajpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item implements Persistable<String> {

    //generatedValue 전략 사용 안할 경우
    @Id
    private String id;

    @CreatedDate
    private LocalDateTime createDate;

    public Item(String id) {
        this.id = id;
    }

    // createDate 가 null 이면 id값 존재x
    @Override
    public boolean isNew() {
        return createDate == null;
    }
}
