package jpa.demo.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Coupon {

    @Id
    @GeneratedValue
    @Column(name = "coupon_id")
    private Long id;

    @JoinColumn(name = "orders_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Orders order;

    private String name;

}
