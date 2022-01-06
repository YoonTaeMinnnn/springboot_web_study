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

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)  //cascade.all => order을 persist하면 orderitems도 다 persist함
    private List<OrderItem> orderItems = new ArrayList<>();  //양방향 매핑을 위한 예제

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="delivery_id")   //delivery_id=> 외래키
    private Delivery delivery;

    private LocalDateTime orderDate;      //시간 정보

    private OrderStatus status;

    //연관관계 메소드(양방향 매핑 시, 값을 한번에 넣어주기 위한 메소드) ----------------------------------------------

    public void setMember(Member member){
        this.member= member;
        member.getOrders().add(this);
    }
    public void addOrderItem(OrderItem orderItems){
        this.orderItems.add(orderItems);
        orderItems.setOrder(this);
    }
    public void setDelivery(Delivery delivery){
        this.delivery=delivery;
        delivery.setOrder(this);
    }

    //주문 생성 메소드 ------------------------------------------------(정적팩토리메소드 패턴)
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //비즈니스 로직 ------------------------------------------------------------------------
    //주문 취소
    public void cancel() {
        if (delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다");
        }

        this.setStatus(OrderStatus.CANCEL);
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel();                         //주문했던 상품 재고 플러스
        }
    }

    //전체 주문 가격 조회//
    public int getTotalPrice() {
        int totalPrice=0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }


}
