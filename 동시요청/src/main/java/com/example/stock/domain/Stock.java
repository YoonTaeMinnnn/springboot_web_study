package com.example.stock.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Stock {

    @Id
    @GeneratedValue
    @Column(name = "stock_id")
    private Long id;

    private Long productId;

    private Long quantity;

    public Stock() {

    }

    public Stock(Long productId, Long quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getQuantity() {
        return this.quantity;
    }

    public void decrease(Long quantity) {
        if (this.quantity - quantity < 0){
            throw new RuntimeException("재고는 0개 미만이 될 수 없습니다");
        }
        this.quantity -= quantity;
    }
}
