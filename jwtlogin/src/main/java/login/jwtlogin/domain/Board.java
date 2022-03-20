package login.jwtlogin.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;

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

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private MatchingStatus matchingStatus;

    private Integer maxNumber;

    private Integer currentNumber;


}
