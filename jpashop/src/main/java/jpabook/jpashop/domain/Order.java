package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id")  //member_id가 외래키
    private Member member;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();  //양방향 매핑을 위한 예제

    @OneToOne
    @JoinColumn(name="delivery_id")   //delivery_id가 외래키
    private Delivery delivery;

    private LocalDateTime orderDate;      //시간 정보

    private OrderStatus Status;

}
