package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    /**
     * dto 반환 시, dto 안에 엔티티 그대로의 필드가 존재하면 안됨.
     * 특정 api 에 맞는 필드값만으로 구성하는 것을 권장
     * 어마어마한 n+1 문제 발생 (지연로딩이 많아서)
     */
    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV1() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        return orders.stream().map(order -> new OrderDto(order)).collect(Collectors.toList());
    }

    /**
     * fetch join 사용한 컬렉션 조회 (일대다)
     * distinct 사용 => 데이터 중복 방지 
     */
    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV2() {
        return orderRepository.findAllWithItem().stream().map(order -> new OrderDto(order)).collect(Collectors.toList());
    }



    @Data
    static class OrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;

        public OrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            orderItems = order.getOrderItems().stream().map(orderItem -> new OrderItemDto(orderItem)).collect(Collectors.toList());
        }

    }

    @Data
    static class OrderItemDto{
        private String itemName;
        private int price;
        private int count;

        public OrderItemDto(OrderItem orderItem){
            itemName = orderItem.getItem().getName();
            price = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }
}
