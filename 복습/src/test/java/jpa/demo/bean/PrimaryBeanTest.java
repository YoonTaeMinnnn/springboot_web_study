package jpa.demo.bean;

import jpa.demo.controller.annotation.MainOrderService;
import jpa.demo.service.discountpolicy.DiscountPolicy;
import jpa.demo.service.discountpolicy.FixedDiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PrimaryBeanTest {

//    @Autowired private OrderService orderService;
    @Autowired @MainOrderService DiscountPolicy discountPolicy;
    @Test
    @DisplayName("@Primary")
    public void primaryTest(){
//        assertThat(orderService.order()).isEqualTo("A 주문 호출");
        assertThat(discountPolicy).isInstanceOf(FixedDiscountPolicy.class);
    }
}
