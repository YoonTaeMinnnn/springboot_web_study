package jpabook.jpashop.service;

import jpabook.jpashop.api.OrderApiController;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.service.dto.OrderDto;
import jpabook.jpashop.service.dto.OrderItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/**
 * dto 변환 로직 (영속성 컨텍스트 + db 커넥션이 관여하는 로직)
 * OSIV 비활성화 시, 필요 ( only repository , service 영역 )
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderQueryService {

    private final OrderRepository orderRepository;


    public List<OrderDto> orderV3() {
        return orderRepository.findAllWithItem()
                .stream()
                .map(order -> new OrderDto(order))
                .collect(Collectors.toList());
    }


}
