package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")   //order테이블의 member 값으로 변경 (없어도됨) 양방향 매핑을 위해 예제를 넣었음
    private List<Order> orders = new ArrayList<>();  //읽기전용(매핑됨)



}
