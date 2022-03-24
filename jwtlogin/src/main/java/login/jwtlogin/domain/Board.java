package login.jwtlogin.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
public class Board {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne
    private Member member;

    @JoinColumn(name = "restaurant_id")
    @OneToOne
    private Restaurant restaurant;

    private String title;

    private String content;

    @CreatedDate
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private MatchingStatus matchingStatus;

    private Integer maxNumber;

    private Integer currentNumber;

//    public static Board createParty(Member member, Restaurant restaurant) {
//        Board board = new Board();
//        board.member = member;
//        board.restaurant = restaurant;
//
//    }


}
