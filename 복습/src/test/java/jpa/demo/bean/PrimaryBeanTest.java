package jpa.demo.bean;

import jpa.demo.controller.annotation.MainOrderService;
import jpa.demo.service.OrderService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PrimaryBeanTest {

//    @Autowired private OrderService orderService;
    @Autowired @MainOrderService OrderService orderService;
    @Test
    @DisplayName("@Primary")
    public void primaryTest(){
//        assertThat(orderService.order()).isEqualTo("A 주문 호출");
        assertThat(orderService.order()).isEqualTo("B 주문 호출");
    }
}
