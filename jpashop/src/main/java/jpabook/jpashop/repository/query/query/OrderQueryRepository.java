package jpabook.jpashop.repository.query.query;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    /**
     * orderItmes 컬렉션 값 set
     */
    public List<OrderQueryDto> findOrderQueryDtos() {
        List<OrderQueryDto> results = findOrders();
        results.forEach(
                o -> {
                    List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
                    o.setOrderItems(orderItems);
                }
        );
        return results;
    }

    public List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery("select new jpabook.jpashop.repository.query.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count) " +
                "from OrderItem oi join oi.item i where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    /**
     * 컬렉션은 dto로 바로 조회 불가능
     */
    public List<OrderQueryDto> findOrders() {
        return em.createQuery("select new jpabook.jpashop.repository.query.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address) " +
                "from Order o join o.member m" +
                " join o.delivery d", OrderQueryDto.class).getResultList();
    }


}
