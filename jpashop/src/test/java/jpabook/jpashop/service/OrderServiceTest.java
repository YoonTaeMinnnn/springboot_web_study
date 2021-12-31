package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired OrderService orderService;
    @Autowired
    OrderRepository orderRepository;


    @Test
    public void 상품주문() {

        //given
        Member member = createMember();
        Book book = createBook();

        //when
        int orderCount=2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertThat(getOrder.getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(1).isEqualTo(getOrder.getOrderItems().size());
        assertThat(10000*orderCount).isEqualTo(getOrder.getTotalPrice());
        assertThat(book.getStockQuantity()).isEqualTo(8);

    }

    @Test
    public void 상품주문_재고수량초과() {
        Member member = createMember();
        Book book = createBook();

        int orderCount=11;
        org.junit.jupiter.api.Assertions.assertThrows(
                NotEnoughStockException.class, () -> orderService.order(member.getId(), book.getId(), orderCount)
        );

    }

    @Test
    public void 주문취소() {
        //given
        Member member = createMember();
        Book book = createBook();
        Long orderId = orderService.order(member.getId(), book.getId(), 2);

        //when
        orderService.cancel(orderId);
        Order order = orderRepository.findOne(orderId);
        //then
        Assertions.assertThat(OrderStatus.CANCEL).isEqualTo(order.getStatus());
        Assertions.assertThat(book.getStockQuantity()).isEqualTo(10);
    }

    private Member createMember(){
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울","강가","123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(){
        Book book = new Book();
        book.setName("시골 jpa");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);
        return book;
    }


}