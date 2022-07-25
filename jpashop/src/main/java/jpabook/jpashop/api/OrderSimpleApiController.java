package jpabook.jpashop.api;

import javassist.Loader;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.SimpleOrderQueryDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * x to one (ManyToOne , OneToOne)
 * order -> member
 * order -> delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    /**
     *  연관객체 무한루프 문제 + 프록시 객체 => type error 문제 발생
     *  사용 금지
     */
    @GetMapping("/api/v1/simple-orders")
    public List<Order> orderV1() {
        return orderRepository.findAllByString(new OrderSearch());
    }

    /**
     * 성능 저하, lazy => 조회쿼리 발생 ( n+1 문제 발생 )
     */
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> orderV2() {
        return orderRepository.findAllByString(new OrderSearch())
                .stream().map(order -> new SimpleOrderDto(order.getId()
                        , order.getMember().getName(), order.getOrderDate(), order.getStatus(), order.getDelivery().getAddress()))
                .collect(Collectors.toList());
    }

    /**
     * fetch join 을 사용한 order 조회 ( n+1 문제 발생 x)
     * @return
     */
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> orderV3() {
        return orderRepository.findAllWithMemberDelivery()
                .stream().map(order -> new SimpleOrderDto(order.getId()
                        , order.getMember().getName(), order.getOrderDate(), order.getStatus(), order.getDelivery().getAddress()))
                .collect(Collectors.toList());
    }

    @GetMapping("/api/v4/simple-orders")
    public List<SimpleOrderQueryDto> orderV4() {
        return orderRepository.findOrderDtos();
    }


    @Data
    @AllArgsConstructor
    static class SimpleOrderDto {
        private Long orderId;

        private String name;

        private LocalDateTime orderDate;

        private OrderStatus orderStatus;

        private Address address;

    }
}
