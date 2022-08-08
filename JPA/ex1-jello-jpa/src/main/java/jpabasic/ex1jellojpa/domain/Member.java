package jpabasic.ex1jellojpa.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @OneToOne(fetch = FetchType.LAZY)  //Locker 객체는 프록시 객체로 가져오고, Locker객체의 속성 접근 시 프록시 초기화 됨
    @JoinColumn(name = "locker_id")
    private Locker locker;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;


    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")
    List<Order> orders = new ArrayList<>();

    @Builder
    public Member(String name) {
        this.name = name;
    }


}
