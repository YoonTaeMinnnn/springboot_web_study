package login.jwtlogin.domain.email;

import login.jwtlogin.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class ConfirmationToken {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(length = 36, name = "token_id")
    private String id;

    private LocalDateTime expiredDate;

    private boolean expired;


//    @Column(name = "member_id")
//    private Long memberId;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public static ConfirmationToken createEmailConfirmationToken() {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.expiredDate = LocalDateTime.now().plusMinutes(5);  //5분 후 만료;
        confirmationToken.expired = false;
        return confirmationToken;
    }

    public void useToken() {
        this.expired = true;
    }
}
