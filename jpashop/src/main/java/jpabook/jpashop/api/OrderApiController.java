package jpabook.jpashop.api;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.query.query.OrderFlatDto;
import jpabook.jpashop.repository.query.query.OrderQueryDto;
import jpabook.jpashop.repository.query.query.OrderQueryRepository;
import jpabook.jpashop.service.OrderQueryService;
import jpabook.jpashop.service.dto.OrderDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * x to many
 * 컬렉션 조회
 */
@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderQueryService orderQueryService;
    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/test")
    public void test() {
        orderRepository.findAllByString(new OrderSearch());
    }

    /**
     * dto 반환 시, dto 안에 엔티티 그대로의 필드가 존재하면 안됨.
     * 특정 api 에 맞는 필드값만으로 구성하는 것을 권장
     * 어마어마한 n+1 문제 발생 (지연로딩이 많아서)
     */
    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        return orders.stream().map(order -> new OrderDto(order)).collect(Collectors.toList());
    }

    /**
     * fetch join 사용한 컬렉션 조회 (일대다)
     * distinct 사용 => 데이터 중복 방지 
     */
    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3() {
        return orderRepository.findAllWithItem().stream().map(order -> new OrderDto(order)).collect(Collectors.toList());
    }

    /**
     * osiv 비활성시, 필요 컨트롤러 (크고 복잡한 어플리케이션 개발 시, 관심사 분리가 중요)
     */
    @GetMapping("/api/v3/osiv/orders")
    public List<jpabook.jpashop.service.dto.OrderDto> ordersV3_osiv() {
        return orderQueryService.orderV3();
    }

    /**
     * to one => 페치조인 잡고,
     * to many => 배치사이즈로 최적화
     * orderItems는 페치조인 x => 배치 사이즈로 추가쿼리 최적화
     * @param offset
     * @param limit
     * @return
     */
    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(@RequestParam(value = "offset", defaultValue = "0") int offset
                                        ,@RequestParam(value = "limit", defaultValue = "100") int limit){
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);

        return orders.stream().map(order -> new OrderDto(order)).collect(Collectors.toList());
    }

    /**
     * dto 컬렉션 조회 => n+1문제
     * @return
     */
    @GetMapping("/api/v4/orders")
    public List<OrderQueryDto> ordersV4(){
        List<OrderQueryDto> orderQueryDtos = orderQueryRepository.findOrderQueryDtos();
        return orderQueryDtos;
    }

    /**
     * dto 컬렉션 조회 - in 쿼리 사용 1+1 최적화
     * @return
     */
    @GetMapping("/api/v5/orders")
    public List<OrderQueryDto> ordersV5(){
        List<OrderQueryDto> orders = orderQueryRepository.findAllByDto_optimization();

        return orders;
    }

    /**
     * dto 컬렉션 조회 - 최적화
     * @return
     */
    @GetMapping("/api/v6/orders")
    public List<OrderQueryDto> ordersV6(){
        List<OrderFlatDto> orders = orderQueryRepository.findAllByDto_flat();
        //OrderQueryDto로 변환하고 반환하면 된다.
        return null;
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
