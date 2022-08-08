package jpabasic.ex1jellojpa.domain.item;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")  //조인 상속 타입이어도 넣어주는게 좋음 (데이터베이스 내에서 별도 조회시 구분역할)
public class Item {                    //싱글 테이블 전략에서는 꼭 들어가야함!!

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int stockQuantity;

}
