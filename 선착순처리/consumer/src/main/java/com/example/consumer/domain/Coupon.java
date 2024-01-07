package com.example.consumer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Coupon {

    @Id
    @GeneratedValue
    @Column(name = "coupon_id")
    private Long id;

    private Long userId;

    public Coupon() {
    }

    public Coupon(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }
}
