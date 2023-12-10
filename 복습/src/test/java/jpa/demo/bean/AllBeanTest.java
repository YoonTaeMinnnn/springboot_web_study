package jpa.demo.bean;

import jpa.demo.service.DiscountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class AllBeanTest {

    @Autowired private DiscountService discountService;

    // 전략 패턴
    @Test
    public void strategyTest() {
        double price = discountService.discount(10000, "fixedDiscountPolicy");
        assertThat(price).isEqualTo(9000.0);
    }
}
