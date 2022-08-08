package jpabook.jpashop.service.dto;

import jpabook.jpashop.domain.OrderItem;
import lombok.Data;

@Data
public class OrderItemDto {

    private String itemName;
    private int price;
    private int count;

    public OrderItemDto(OrderItem orderItem){
        itemName = orderItem.getItem().getName();
        price = orderItem.getOrderPrice();
        count = orderItem.getCount();
    }
}
