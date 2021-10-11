package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")  //member_id가 외래키
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();  //양방향 매핑을 위한 예제

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="delivery_id")   //delivery_id=> 외래키
    private Delivery delivery;

    private LocalDateTime orderDate;      //시간 정보

    private OrderStatus Status;

    //연관관계 메소드(양방향 매핑 시, 값을 한번에 넣어주기 위한 메소드)

    public void setMember(Member member){
        this.member= member;
        member.getOrders().add(this);
    }
    public void setOrderItems(OrderItem orderItems){
        this.orderItems.add(orderItems);
        orderItems.setOrder(this);
    }
    public void setDelivery(Delivery delivery){
        this.delivery=delivery;
        delivery.setOrder(this);
    }


}
